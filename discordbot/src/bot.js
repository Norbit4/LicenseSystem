const { Client, Status } = require('discord.js');
const { init } = require('./commands/commandregistry');

require('dotenv').config();

const client = new Client({ intents: [] });

const config = require('../settings/config.json');
const { token, status } = config;

if(!token){
    console.log('[ERROR] You must provide a token!');
    return;
}

client.login(token).then(() =>{
    client.user.setStatus(Status.Ready);
    client.user.setActivity(status);

    console.log(`Logged in as ${client.user.tag}!`);

    init(client);
});