# CDE-Simulation-Project-International-Patients-Treatment-Management

## Authors :

<table>
  <tr>
      <td>
        <a href="https://github.com/DRoy7"><td>Deep Roy</td></a>
        </td>
      <td>
        <a href="https://github.com/sriharish252"><td>Sri Harish</td></a>
        </td>
      <td>
        <a href="https://github.com/Kamalesh8"><td>Kamalesh R</td></a>
        </td>
      <td>
        <a href="https://github.com/Megha0699"><td>Megha S</td></a>
        </td>
      <td>
        <a href="https://github.com/greninja199"><td>Praduman Kumar</td></a>
        </td>
    </tr>
</table>

## Modules::

### Authorizatiion-Microservice :
This module is used for doing the **Authentication** and **Authorization** part of our project. 
This microsevice provides the endpoints for authentication and authorization

#### --Endpoints : 
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
#### --Application Properties Toggle :
spring.application.name=Authorizatiion-Microservice<br/>
server.port=8400<br/>
server.servlet.context-path=/auth<br/>
User Database : H2(In-Memory)<br/>
