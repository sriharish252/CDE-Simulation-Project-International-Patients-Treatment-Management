# CDE-Simulation-Project-International-Patients-Treatment-Management

## Authors :

<table>
  <tr>
      <td>
        <a href="https://github.com/DRoy7">Deep Roy</a>
        </td>
      <td>
        <a href="https://github.com/sriharish252">Sri Harish</a>
        </td>
      <td>
        <a href="https://github.com/Kamalesh8">Kamalesh R</a>
        </td>
      <td>
        <a href="https://github.com/Megha0699">Megha S</a>
        </td>
      <td>
        <a href="https://github.com/greninja199">Praduman Kumar</a>
        </td>
    </tr>
</table>

## Modules::

* ### Authorization-Microservice :
  This module is used for doing the **Authentication** and **Authorization** part of our project. 
  This microsevice provides the endpoints for authentication and authorization

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>POST</td>
                <td>/authenticate</td>
                <td>ResponseEntity</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/authorize</td>
                <td>boolean</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/authorize-role</td>
                <td>boolean</td>
            </tr>
        </tbody>
    </table>

  * #### --Dependencies on Other microsevices : **None**

  * #### --Application Properties Toggle :<br/>
      spring.application.name=Authorizatiion-Microservice<br/>
      server.port=8400<br/>
      server.servlet.context-path=/auth<br/>
      User Database : H2(In-Memory)<br/>

* ### InsuranceClaim-Microservice :
  This module is used for doing the **Insurance Claim** part after the registration of a patient. 
  This microsevice provides the endpoints for getting the Insurance related details and storing insurer details.

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>GET</td>
                <td>/getAllInsurerDetail</td>
                <td>List of "InsurerDetail"</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/getInsurerByPackageName/{packageName}</td>
                <td>"InsurerDetail"</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/initiateClaim</td>
                <td>double</td>
            </tr>
        </tbody>
    </table>

  * #### --Dependencies on Other microsevices : **Authorization-Microservice**, **IPTreatment-MicroService**

  * #### --Application Properties Toggle :<br/>
      spring.application.name=InsuranceClaim<br/>
      server.port=8300<br/>
      server.servlet.context-path=/insure<br/>
      auth.URL=http://localhost:8400/auth<br/>
      iptreatment.URL=http://localhost:8200/ipTreatment<br/>
      User Database : H2(In-Memory)<br/>


* ### IPTreatmentOffering-Microservice :
  This module is used for doing the **IPTreatmentOffering services**. 
  This microsevice provides the endpoints for viewing and modifying the treatment packages and the specialists.

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>GET</td>
                <td>/ipTreatmentPackages</td>
                <td>List of "IPTreatmentPackage"</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/ipTreatmentPackageByName/{ailment}/{packageName}</td>
                <td>List of "IPTreatmentPackage"</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/specialists</td>
                <td>List of "SpecialistDetail"</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/addSpecialist</td>
                <td>ResponseEntity of String type</td>
            </tr>
            <tr>
                <td>DELETE</td>
                <td>/deleteSpecialist/{specialistId}</td>
                <td>ResponseEntity of String</td>
            </tr>
            <tr>
                <td>PUT</td>
                <td>/updatePackage/{pid}/{treatmentPackageName}</td>
                <td>ResponseEntity of String</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/countPid/{pid}</td>
                <td>boolean</td>
            </tr>
        </tbody>
    </table>

  * #### --Dependencies on Other microsevices : **Authorization-Microservice**

  * #### --Application Properties Toggle : <br/>
      spring.application.name=IPTreatmentOffering-service<br/>
      server.port=8100<br/>
      server.servlet.context-path=/ipTreatmentOffering<br/>
      User Database : H2(In-Memory)<br/>

* ### IPTreatment-Microservice :
  This module is used for doing the **Treatment Plan** details of our project. 
  This microsevice provides the endpoints for formulating timetable , getting treatment plans & updating the status.

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>POST</td>
                <td>/formulateTimetable</td>
                <td>TreatmentPlan</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/getAllTreatmentPlan</td>
                <td>List of "TreatmentPlan"</td>
            </tr>
            <tr>
                <td>POST</td>
                <td>/updateTreatmentPlan</td>
                <td>TreatmentPlan</td>
            </tr>
            <tr>
                <td>GET</td>
                <td>/health-check</td>
                <td>ResponseEntity</td>
            </tr>
        </tbody>
    </table>
    
  * #### --Dependencies on Other microsevices : **Authorization-Microservice**, **IPTreatmentOffering-Microservice**

  * #### --Application Properties Toggle : <br/>
      spring.application.name=IPTreatment-service<br/>
      server.port=8200<br/>
      server.servlet.context-path=/ipTreatment<br/>
      User Database : H2(In-Memory)<br/>

* ### Portal-Microservice :
  This module is used for simulating the project through the **User Interface**. 
  This microsevice provides the endpoints for **Login**. This is the UI-Microservice for our Project.

  * #### --Endpoints : 
    <table>
        <thead>
            <th>Method</th>
            <th>Endpoint Path</th>
            <th>Returns</th>
        </thead>
        <tbody>
            <tr>
                <td>GET</td>
                <td>/login</td>
                <td>View</td>
            </tr>
        </tbody>
    </table>
    
  * #### --Dependencies on Other microsevices : **Authorization-Microservice**, **IPTreatmentOffering-Microservice**, **IPTreatment-MicroService**, **InsuranceClaim-Microservice**

  * #### --Application Properties Toggle : <br/>
      auth.URL=http://localhost:8400/auth<br/>
      ipoffering.URL=http://localhost:8100/ipTreatmentOffering<br/>
      iptreatment.URL=http://localhost:8200/ipTreatment<br/>
      insure.URL=http://localhost:8300/insure<br/>




