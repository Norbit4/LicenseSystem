const { Client, GatewayIntentBits, Status, TextInputStyle } = require('discord.js')

const client = new Client({ intents: [GatewayIntentBits.Guilds, GatewayIntentBits.GuildVoiceStates,
        GatewayIntentBits.GuildMessages, GatewayIntentBits.MessageContent,
     GatewayIntentBits.GuildMembers]})

const config = require('../settings/config.json')

const { CmdBuilder } = require("./utils/commandbuilder");
const { FormBuilder } = require("./utils/formbuilder");
const { BotManager } = require("./utils/botutils");

const { token, status, guildID } = config


if(!token){
    console.log('[ERROR] You must provide a token!')
    return
}

client.login(token).then(() =>{
    client.user.setStatus(Status.Ready)
    client.user.setActivity(status)

    console.log(`Logged in as ${client.user.tag}!`);

    const botManager =  new BotManager(client)

    const formTemplate = new FormBuilder('tytuł')
        .addTextInput('1', 'tekst', TextInputStyle.Paragraph)
        .addTextInput('2', 'tekst', TextInputStyle.Short)
        .addExecute(e =>{
            e.reply('works ' + e.fields.getTextInputValue('1'))
        })
        .build()

    let form = botManager.createForm(formTemplate);

    const startCmd = new CmdBuilder("test","test desc")
        .addExecute((e) => {

            e.showModal(form);
        })
        .build();

    botManager.registerCommand(startCmd)
});