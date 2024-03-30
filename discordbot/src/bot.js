const { Client, Status } = require('discord.js');
const { init } = require('./commands/commandregistry');

require('dotenv').config();

const botToken = process.env.BOT_TOKEN;
const botActivity = process.env.BOT_ACTIVITY;

const client = new Client({ intents: [] });


if(!botToken){
    console.log('[ERROR] You must provide a token!');
    return;
}

client.login(botToken).then(() =>{
    client.user.setStatus(Status.Ready);
    if(botActivity) client.user.setActivity(botActivity);

    console.log(`Logged in as ${client.user.tag}!`);

    init(client);
});