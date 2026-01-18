#!/bin/bash

#Definicion colores
VERDE='\033[0;32m'
ROJO='\033[0;31m'
NARANJA='\033[0;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color (reset)

#Definiciones objetos
profesores=(
  '{"apellidos": "Ruiz Méndez", "dni": "11112222A", "nombre": "Laura", "password": "prof123"}'
  '{"apellidos": "García López", "dni": "22223333B", "nombre": "Miguel", "password": "clave456"}'
  '{"apellidos": "Santos Herrera", "dni": "33334444C", "nombre": "Elena", "password": "pw789"}'
  '{"apellidos": "Morales Díaz", "dni": "44445555D", "nombre": "Javier", "password": "abcd1234"}'
  '{"apellidos": "Torres Navarro", "dni": "55556666E", "nombre": "Cristina", "password": "pass321"}'
)

alumnos=(
  '{"apellidos": "López Martín", "password": "abcdef", "nombre": "Ana", "dni": "23456789X"}'
  '{"apellidos": "Pérez Gómez", "password": "qwerty", "nombre": "Luis", "dni": "34567890Y"}'
  '{"apellidos": "Martín Ruiz", "password": "pass123", "nombre": "Clara", "dni": "45678901Z"}'
  '{"apellidos": "Serrano Díaz", "password": "clave456", "nombre": "Raúl", "dni": "56789012A"}'
  '{"apellidos": "Gómez Nieto", "password": "luzazul", "nombre": "Sofía", "dni": "67890123B"}'
  '{"apellidos": "Fernández Ríos", "password": "uno234", "nombre": "Iván", "dni": "78901234C"}'
  '{"apellidos": "Ramírez Salas", "password": "pass789", "nombre": "Natalia", "dni": "89012345D"}'
  '{"apellidos": "Navarro Torres", "password": "torres1", "nombre": "Daniel", "dni": "90123456E"}'
  '{"apellidos": "Cano Vega", "password": "abc123", "nombre": "Lucía", "dni": "01234567F"}'
  '{"apellidos": "Moreno Castillo", "password": "clave789", "nombre": "Óscar", "dni": "12345098G"}'
)

asignaturas=(
  '{"acronimo": "ASO", "creditos": 4.5, "cuatrimestre": "A", "curso": 3, "nombre": "Administración de Sistemas Operativos"}'
  '{"acronimo": "PAI", "creditos": 6, "cuatrimestre": "B", "curso": 3, "nombre": "Inteligencia Artificial Práctica"}'
  '{"acronimo": "FBD", "creditos": 6, "cuatrimestre": "A", "curso": 2, "nombre": "Fundamentos de Bases de Datos"}'
  '{"acronimo": "IDS", "creditos": 4.5, "cuatrimestre": "A", "curso": 3, "nombre": "Ingeniería de Datos a Gran Escala"}'
  '{"acronimo": "CSI", "creditos": 6.0, "cuatrimestre": "B", "curso": 3, "nombre": "Ciberseguridad e Inteligencia Artificial"}'
)

# Función que, dada un DNI, devuelve el password de los profesores
# Si el profesor esta por defecto en Centro Educativo, devuelve el valor por defecto "123456"
get_password() {
  local buscado="$1"
  for prof_json in "${profesores[@]}"; do
    # Extraigo el campo "dni" de este objeto JSON
    dni="$(jq -r '.dni' <<<"$prof_json")"
    if [[ "$dni" == "$buscado" ]]; then
      # Si coincide, extraigo y devuelvo la contraseña
      jq -r '.password' <<<"$prof_json"
      return
    fi
  done
  # Si no salió del bucle, no se encontró: devolvemos el valor por defecto
  echo "123456"
}

#Conseguir key admin
KEY=$(curl -s --data '{"dni":"111111111","password":"654321"}' \
-X POST -H "content-type: application/json" \
http://localhost:9090/CentroEducativo/login -c cucu -b cucu)


#Añadir nuevas asignaturas
asignaturasAnadidas=0

echo -e "${BLUE} ---- Añadiendo asignaturas... ----${NC}"
for asignatura in "${asignaturas[@]}"; do
  ADDED=$(curl -s \
  --data "$asignatura" \
  -X POST \
  -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas?key=$KEY" \
  -c cucu -b cucu)

  if [ $ADDED == 'OK' ]; then
    ((asignaturasAnadidas++))
  else
    echo -e "${NARANJA}[*] ERROR AÑADIENDO ASIGNATURA: ${asignatura}${NC}"
  fi
done

if [ "$asignaturasAnadidas" -eq "${#asignaturas[@]}" ]; then
  echo -e "${VERDE}[+] Se han añadido todas las asignaturas.${NC}"
else
  echo -e "${ROJO}[!] Se han añadido ${asignaturasAnadidas}/${#asignaturas[@]} asignaturas${NC}"
fi


#Añadir nuevos alumnos
alumnosAnadidos=0

echo -e "${BLUE} ---- Añadiendo alumnos... ----${NC}"
for alumno in "${alumnos[@]}"; do
  ADDED=$(curl -s \
  --data "$alumno" \
  -X POST \
  -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/alumnos?key=$KEY" \
  -c cucu -b cucu)

  if [ $ADDED == 'OK' ]; then
    ((alumnosAnadidos++))
  else
    echo -e "${NARANJA}[*] ERROR AÑADIENDO ALUMNO: ${alumno}${NC}"
  fi
done

if [ "$alumnosAnadidos" -eq "${#alumnos[@]}" ]; then
  echo -e "${VERDE}[+] Se han añadido todos los alumnos.${NC}"
else
  echo -e "${ROJO}[!] Se han añadido ${alumnosAnadidos}/${#alumnos[@]} alumnos${NC}"
fi


#Añadir nuevos profesores
profesoresAnadidos=0
indiceAsignatura=0

echo -e "${BLUE} ---- Añadiendo profesores... ----${NC}"
for profesor in "${profesores[@]}"; do
  ADDED=$(curl -s \
  --data "$profesor" \
  -X POST \
  -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/profesores?key=$KEY" \
  -c cucu -b cucu)

  if [ $ADDED == 'OK' ]; then
    ((profesoresAnadidos++))

    #Sacamos el dni del profesor añadido
    dni=$(echo "$profesor" | jq -r '.dni')

    #Sacamos el acronimo de la asignatura correspondiente
    asignatura="${asignaturas[$indiceAsignatura]}"
    acronimo=$(echo "$asignatura" | jq -r '.acronimo')
    ((indiceAsignatura=(indiceAsignatura+1)%${#asignaturas[@]}))

    PROFESORAASIGNATURA=$(curl -s \
    --data "$dni" \
    -X POST \
    -H "content-type: application/json" \
    "http://localhost:9090/CentroEducativo/asignaturas/${acronimo}/profesores?key=$KEY" \
    -c cucu -b cucu)

    if [ $PROFESORAASIGNATURA == "OK" ]; then
      echo -e "${VERDE}[+] El profesor con dni ${dni} se ha añadido a la asignatura ${acronimo}.${NC}"
    fi

  else
    echo -e "${NARANJA}[*] ERROR AÑADIENDO PRODFESOR: ${profesor}${NC}"
  fi
done

if [ "$profesoresAnadidos" -eq "${#profesores[@]}" ]; then
  echo -e "${VERDE}[+] Se han añadido todos los profesores.${NC}"
else
  echo -e "${ROJO}[!] Se han añadido ${profesoresAnadidos}/${#profesores[@]} profesores${NC}"
fi


#Añadir alumnos a asignaturas
alumnosAnadidos=0
indiceAsignatura=0

echo -e "${BLUE} ---- Añadiendo alumnos a asignaturas... ----${NC}"
for alumno in "${alumnos[@]}"; do
  #Sacamos el dni del alumno añadido
  dni=$(echo "$alumno" | jq -r '.dni')

  #Sacamos el acronimo de la asignatura correspondiente
  asignatura="${asignaturas[$indiceAsignatura]}"
  acronimo=$(echo "$asignatura" | jq -r '.acronimo')

  ADDED=$(curl -s \
  --data "$dni" \
  -X POST \
  -H "content-type: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas/${acronimo}/alumnos?key=$KEY" \
  -c cucu -b cucu)

  ((indiceAsignatura=(indiceAsignatura+1)%${#asignaturas[@]}))

  if [ "$ADDED" = "OK" ]; then
    echo -e "${VERDE}[+] Alumno ${dni} añadido a ${acronimo}.${NC}"
    ((alumnosAnadidos++))
  else
    echo -e "${NARANJA}[*] ERROR añadiendo alumno ${dni} a ${acronimo}.${NC}"
  fi
done

if [ "$alumnosAnadidos" -eq "${#alumnos[@]}" ]; then
  echo -e "${VERDE}[+] Se han añadido todos los alumnos.${NC}"
else
  echo -e "${ROJO}[!] Se han añadido ${alumnosAnadidos}/${#alumnos[@]} alumnos${NC}"
fi


# Asignar una nota aleatoria a un alumno en una asignatura
echo -e "${BLUE} ---- Añadiendo notas a los alumnos... ----${NC}"
asignaturas_completas=$(curl -s \
  -X GET \
  -H  "accept: application/json" \
  "http://localhost:9090/CentroEducativo/asignaturas?key=$KEY" \
  -c cucu -b cucu)

echo "$asignaturas_completas" \
  | jq -c '.[]' \
  | while read -r asignatura; do
  acronimo=$(jq -r '.acronimo' <<<"$asignatura")

  alumnos_asignados=$(curl -s \
    -X GET \
    -H "accept: application/json" \
    "http://localhost:9090/CentroEducativo/asignaturas/${acronimo}/alumnos?key=$KEY" \
    -c cucu -b cucu)

    profs_asignados=$(curl -s \
    -X GET \
    -H "accept: application/json" \
    "http://localhost:9090/CentroEducativo/asignaturas/${acronimo}/profesores?key=$KEY" \
    -c cucu -b cucu)

    dni_profesor=$(echo "$profs_asignados" | jq -r '.[0].dni')
    pass_profesor=$(get_password "$dni_profesor")

    # Archivo temporal de cookies para el profesor
    PROF_COOKIE=$(mktemp)

    #Conseguir key del profesor
    PKEY=$(curl -s \
      --data "{\"dni\":\"$dni_profesor\",\"password\":\"$pass_profesor\"}" \
      -X POST \
      -H "content-type: application/json" \
      http://localhost:9090/CentroEducativo/login -c "$PROF_COOKIE" -b "$PROF_COOKIE")
    echo -e "${NARANJA}[INFO] Asignando notas para la asignatura ${acronimo} con profesor ${dni_profesor}.${NC}"

    echo "$alumnos_asignados" \
      | jq -r '.[].alumno' \
      | while read -r dni_alumno; do
      
      nota=$(LC_NUMERIC=C awk -v min=0 -v max=10 'BEGIN {srand(); printf "%.1f", min+rand()*(max-min)}')

      # Lanza el PUT para fijar la nota; el cuerpo es solo la cadena con la nota:
      STATUS=$(curl -s \
        --data $nota \
        -X PUT \
        -H "content-type: application/json" \
        "http://localhost:9090/CentroEducativo/alumnos/${dni_alumno}/asignaturas/${acronimo}?key=$PKEY" \
        -c "$PROF_COOKIE" -b "$PROF_COOKIE")

      if [ "$STATUS" = "OK" ]; then
        echo -e "${VERDE}[+] Nota $nota asignada a ${dni_alumno} en ${acronimo}.${NC}"
      else
        echo -e "${ROJO}[!] Error asignando nota $nota a ${dni_alumno} en ${acronimo}.${NC}"
      fi
    done
    # Limpiar el archivo temporal de cookies del profesor
    rm -f "$PROF_COOKIE"
done