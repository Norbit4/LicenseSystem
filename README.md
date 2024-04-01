<h1 align="left"><b>License System</b></h1>


The License System project provides full control over the application license system through an API with built-in authorization. It allows quick management of licenses, including creating, editing, deleting and viewing information. Integration with the Discord bot allows you to generate reports, check the validity of licenses and assign them to users, making administration much easier.

API is dedicate to minecraft plugins but can by used in other products and apps.

<h5 align="left">Used Languages and Tools:</h5>

<p align="left">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,js,discordjs,nodejs,docker,mysql"/>
  </a>
</p>


<h2 align="left" id="work">Table of contents</h2>

- [How does it work?](#work)
- [Documentation](#doc)
- [Discord bot](#bot)
- [Example implementation](#implementation)


<h2 align="left" id="work">How does it work?</h2>

- 

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

<h2 align="left" id="doc">Documentation</h2>

Documentation was created using [OpenApi](https://github.com/OAI)

- <b> local env: http://localhost:8080/swagger-ui/index.html </b>


 <i>preview:</i>
![doc_example](https://github.com/Norbit4/LicenseSystem/assets/46154743/d1097c45-80ed-4bc4-bfa0-dff6c6ae417d)


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


<h2 align="left" id="implementation">Example implementation</h2>

[Click to view](https://github.com/Norbit4/LicenseSystem/tree/master/example/exampleplugin/src/main/java/pl/norbit/exampleplugin)
