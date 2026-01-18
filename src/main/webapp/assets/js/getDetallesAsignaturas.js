let alumnosData = [];
let currentAlumnoIndex = 0;
let acronimoAsignatura = '';

window.onload = async function () {
  const params = new URLSearchParams(window.location.search);
  acronimoAsignatura = params.get('acronimo'); 
  const titulo = document.getElementById("tituloAsignatura");

  // Elementos UI
  const btnAnterior = document.getElementById('btn-anterior');
  const btnSiguiente = document.getElementById('btn-siguiente');

  try {
    const response = await fetch(`/NotasOnline/detallesAsignatura?acronimo=${acronimoAsignatura}`);
    const data = await response.json();

    titulo.innerHTML = `${data.titulo} (${acronimoAsignatura})`;

    alumnosData = data.alumnos;
    
    if (alumnosData.length > 0) {
      const primerAlumno = alumnosData[0].alumno;
      mostrarExpedienteAlumno(primerAlumno, acronimoAsignatura);
    }

    if (alumnosData.length > 0) {
      btnSiguiente.disabled = false;
    }

  } catch (error) {
    console.error('Error fetching or displaying data:', error);
  }

  btnAnterior.addEventListener('click', () => {
    if (currentAlumnoIndex > 0) {
      currentAlumnoIndex--;
      const dniAlu = alumnosData[currentAlumnoIndex].alumno;
      mostrarExpedienteAlumno(dniAlu, acronimoAsignatura);
      updateNavigationButtons();
    }
  });

  btnSiguiente.addEventListener('click', () => {
    if (currentAlumnoIndex < alumnosData.length - 1) {
      currentAlumnoIndex++;
      const dniAlu = alumnosData[currentAlumnoIndex].alumno;
      mostrarExpedienteAlumno(dniAlu, acronimoAsignatura);
      updateNavigationButtons();
    }
  });
};

function updateNavigationButtons() {
  const btnAnterior = document.getElementById('btn-anterior');
  const btnSiguiente = document.getElementById('btn-siguiente');
  
  btnAnterior.disabled = currentAlumnoIndex === 0;
  btnSiguiente.disabled = currentAlumnoIndex === alumnosData.length - 1;
}

async function getAlumno(dniAlu) {
  const alumnoResponse = await fetch(`/NotasOnline/getAlumno?dni=${dniAlu}`);
  return await alumnoResponse.json();
}

function mostrarExpedienteAlumno(dniAlu, acronimo) {
  window.dniAluSeleccionado = dniAlu;
  window.acronimoSeleccionado = acronimo;
  loadAlumno();
}

async function loadAlumno() {
  const dniAlu = window.dniAluSeleccionado;
  const acronimo = window.acronimoSeleccionado;

  const avatar = document.getElementById('avatar-alumno');
  const nombreAluField = document.getElementById('nombre-alumno');
  const imgPath = `assets/images_alumnos/${dniAlu}.jpg`;
  avatar.setAttribute('src', imgPath);

  try {
    const response = await fetch(`/NotasOnline/getAlumno?dni=${dniAlu}`);
    const data = await response.json();

    nombreAluField.innerHTML = `${data.alumno.apellidos}, ${data.alumno.nombre} (${data.alumno.dni})`;

    const textoRelleno = document.getElementById('texto-relleno');
    textoRelleno.innerHTML = data.relleno;

    const ul = document.getElementById('lista-asignaturas');
    ul.innerHTML = "";

    data.asignaturas.forEach((asignatura) => {
      const li = document.createElement('li');
      li.className = 'list-group-item d-flex justify-content-between align-items-center';
      
      const asignaturaInfo = document.createElement('span');
      asignaturaInfo.innerHTML = `${asignatura.asignatura} - `;
      
      const notaContainer = document.createElement('div');
      notaContainer.className = 'nota-container';
	  
      notaContainer.appendChild(asignaturaInfo);
      li.appendChild(notaContainer);

      if (asignatura.asignatura === acronimo) {
		asignaturaInfo.innerHTML = `${asignatura.asignatura} - ${asignatura.nota}`;      
        
			  
        const editButton = document.createElement('button');
        editButton.className = 'btn btn-sm btn-outline-primary btn-editar';
        editButton.textContent = 'Editar';
        editButton.dataset.asignatura = asignatura.asignatura;
        
        editButton.onclick = () => {
			// extraemos la nota actual
			  const currentNota = asignaturaInfo.textContent.match(/[\d,\.]+/)[0];

			  // creamos el input
			  const input = document.createElement('input');
			  input.type = 'text';
			  input.value = currentNota;
			  input.className = 'form-control form-control-sm';
			  input.style.width = '60px';
			  input.style.display = 'inline-block';

			  // botón Guardar
			  const saveButton = document.createElement('button');
			  saveButton.textContent = 'Guardar';
			  saveButton.className = 'btn btn-sm btn-success ms-2';
			  saveButton.disabled = true;  // empieza deshabilitado

			  // botón Cancelar
			  const cancelButton = document.createElement('button');
			  cancelButton.textContent = 'Cancelar';
			  cancelButton.className = 'btn btn-sm btn-secondary ms-2';

			  // patrón nota 0–10 con coma
			  const notaPattern = /^(?:10(?:,[0-9]+)?|[0-9](?:,[0-9]+)?)$/;

			  // validación: habilita saveButton solo con input válido
			  input.addEventListener('input', () => {
			    const valido = notaPattern.test(input.value.trim());
			    saveButton.disabled = !valido;
			  });

		  asignaturaInfo.innerHTML = `${asignatura.asignatura} - `;
          notaContainer.appendChild(input);
          notaContainer.appendChild(saveButton);
          notaContainer.appendChild(cancelButton);
          li.removeChild(editButton);
		
          saveButton.onclick = async () => {
            const nuevaNota = input.value.replace(",",".");
            try {
              const updateResponse = await fetch('/NotasOnline/getAlumno', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                  dni: dniAlu,
                  acronimo: asignatura.asignatura,
                  nota: nuevaNota
                }),
              });

              if (!updateResponse.ok) throw new Error('Error actualizando nota');

              asignaturaInfo.innerHTML = `${asignatura.asignatura} - ${nuevaNota.replace(".",",")}`;
              notaContainer.removeChild(input);
              notaContainer.removeChild(saveButton);
              notaContainer.removeChild(cancelButton);
              li.appendChild(editButton);
              
              asignatura.nota = nuevaNota;
            } catch (err) {
              console.error('Error guardando nota:', err);
              alert('Error al guardar la nota.');
            }
          };

          cancelButton.onclick = () => {
			asignaturaInfo.innerHTML = `${asignatura.asignatura} - ${currentNota}`;
            notaContainer.replaceChild(asignaturaInfo, input);
            notaContainer.removeChild(saveButton);
            notaContainer.removeChild(cancelButton);
            li.appendChild(editButton);
          };
        };

        li.appendChild(editButton);
      }
	  
	  

      ul.appendChild(li);
    });
  } catch (error) {
    console.error('Error fetching o mostrando alumno:', error);
  }
}
