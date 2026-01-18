# NOL: Academic Management System

**NOL (Notas Online)** is a full-stack Java web application designed for managing academic records. Developed as a group project for the **Desarrollo Web (DEW)** subject at the Polytechnic University of Valencia (UPV), it serves as a centralized platform where students and professors interact with an external educational REST API.

---

### 🚀 Key Features
* **Role-Based Ecosystem:** Implements specialized dashboards for **Students** and **Professors** using Tomcat's container-managed security (`rolalu` and `rolpro`).
* **Dynamic Grade Management:** Allows professors to retrieve subject lists and update student grades in real-time via RESTful API calls.
* **Automated PDF Certification:** Generates official academic transcripts using **Apache PDFBox**, complete with student avatars and formatted course data.
* **Security & Auditing:** Features a custom **Servlet Filter** to log system-wide activity (IP, user, timestamp, and method) for administrative oversight.
* **Persistent Sessions:** Utilizes a custom `FileCookieJar` with **OkHttp** to maintain authenticated session state with the external API across requests.
* **Automated Data Population**: A comprehensive Bash suite utilizing **curl** and **jq** to simulate a realistic academic environment by programmatically initializing subjects, professors, and student enrollments.


### 🛠 Technical Stack
* **Backend:** Java (Jakarta EE Servlets).
* **Frontend:** JSP, Bootstrap 5, HTML5, CSS3, and JavaScript (Fetch API).
* **Server:** Apache Tomcat 10.1.
* **Libraries:** `OkHttp` (Networking), `Gson` (JSON Parsing), and `Apache PDFBox` (PDF Generation).


### ⚙️ Setup & Installation
1.  **Tomcat Configuration:** Integrate the user roles defined in `tomcat-users.xml` into your local Tomcat instance.
2.  **Deployment:** Build the project and deploy the generated WAR file to **Apache Tomcat 10.1**.
3.  **Environment Population:** Initialize the academic database by running the automated suite:
    ```bash
    chmod +x setup.sh
    ./setup.sh
    ```

---