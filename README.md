<h1 align="left"><b>License System</b></h1>


The License System project provides full control over the application license system through an API with built-in authorization. It allows quick management of licenses, including creating, editing, deleting and viewing information. Integration with the Discord bot allows you to generate reports, check the validity of licenses and assign them to users, making administration much easier.

API is dedicate to minecraft plugins but can by used in other products and apps.

<h5 align="left">Used Languages and Tools:</h5>

<p align="left">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,js,discordjs,nodejs,docker,mysql"/>
  </a>
</p>


<h2 align="left" id="content">Table of contents</h2>

- [How does it work?](#work)
- [Documentation](#doc)
- [Discord bot](#bot)
- [Example implementation](#implementation)
- [Tests](#tests)


<h2 align="left" id="work">How does it work?</h2>

<br>

![schema](https://github.com/Norbit4/LicenseSystem/assets/46154743/dcca63ec-6953-41b3-8c13-5e425c0f85f9)

<br>

- *On startup:*

```yml
curl -X 'GET' \
  'http://localhost:8080/api/v1/license/generateServerKey/<licenseKey>' \
  -H 'accept: */*' \
  -H 'Authorization: admin-secret-token'
```   

When the application starts, it performs a reqest to the API to verify if the token key is correct and generate a new serverKey. 
If the license key is correct it sends the new generated serverKey. 

- *On running:*

```yml
curl -X 'PUT' \
  'http://localhost:8080/api/v1/license/isValidServerKey' \
  -H 'accept: */*' \
  -H 'Authorization: admin-secret-token' \
  -H 'Content-Type: application/json' \
  -d '{
  "licenseKey": "string",
  "serverKey": "string"
  }'
```   

The application every x time sends a request to the API to verify if the server key is correct.
If it is not correct it means that another application with the same license key has been started. Then the application closes.

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="doc">Run</h2>

1. Clone repository

```
git clone https://github.com/Norbit4/LicenseSystem
```

2. [Create discord bot](https://discord.com/developers/docs/intro) and invite bot to your server


3. Copy <b>bot token</b> and paste it to <b>docker-compose.yml </b>

4. Start app

```
docker-compose up
```

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="doc">Documentation</h2>

Documentation was created using [OpenApi](https://github.com/OAI)

- <b> local env: http://localhost:8080/swagger-ui/index.html </b>


 <i>preview:</i>
![doc_example](https://github.com/Norbit4/LicenseSystem/assets/46154743/d1097c45-80ed-4bc4-bfa0-dff6c6ae417d)

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="bot">Discord bot</h2>

The discord bot allows easy management of licenses and tokens.

<i>preview:</i>
- Licenses

![license_create](https://github.com/Norbit4/LicenseSystem/assets/46154743/85352472-e3d1-4111-b64a-4dab94a1c053)

![license_check](https://github.com/Norbit4/LicenseSystem/assets/46154743/190be861-74c0-4c47-bf17-14a78eca3670)

- Report
  
![report](https://github.com/Norbit4/LicenseSystem/assets/46154743/353c196e-2779-4b80-8475-379067f9b6f5)

- Tokens

![token_create](https://github.com/Norbit4/LicenseSystem/assets/46154743/219d97b3-1834-4a06-b957-7b30c9f5fb73)

![token_notfoud](https://github.com/Norbit4/LicenseSystem/assets/46154743/81a1b8ca-0403-4977-a34c-0c3d75b6a724)

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="implementation">Example implementation</h2>

[Click to view](https://github.com/Norbit4/LicenseSystem/tree/master/example/exampleplugin/src/main/java/pl/norbit/exampleplugin)


<h2 align="left" id="tests">Tests</h2>

![tests](https://github.com/Norbit4/LicenseSystem/assets/46154743/bce4a16e-ebc0-4029-a552-0bf36b0977c7)

<a href="https://github.com/Norbit4/LicenseSystem/assets/46154743/d5e224ef-a6f0-4bed-a011-e0a7cd39fa2c" target="_blank" rel="noreferrer"> 
<img src="https://github.com/Norbit4/LicenseSystem/assets/46154743/d5e224ef-a6f0-4bed-a011-e0a7cd39fa2c" width=630" alt="logo"/></a>

<div align="right"><a href="#content">Back to top</a></div>
