const { BotManager } = require("../utils/botutils");
const { FormBuilder } = require("../utils/formbuilder");
const { TextInputStyle } = require("discord.js");
const { CmdBuilder } = require("../utils/commandbuilder");

const { licenseForm } = require('../../settings/messages.json');
const { title, ownerLabel, descriptionLabel, timeLabel } = licenseForm

let form;

const createForms = (botManager) =>{
    const formTemplate = new FormBuilder(title)
        .addTextInput('1', ownerLabel, TextInputStyle.Short, true)
        .addTextInput('2', descriptionLabel, TextInputStyle.Paragraph, false)
        .addTextInput('3', timeLabel, TextInputStyle.Short, false)
        .addExecute(e => {
            const owner = e.fields.getTextInputValue('1')
            const description = e.fields.getTextInputValue('2')
            const time = e.fields.getTextInputValue('3')

            e.reply(`${owner}, ${description}, ${time} `)
        })
        .build()

    form = botManager.createForm(formTemplate);
};

const createCommands = (botManager) =>{
    const licenseCmd = new CmdBuilder("license","create new license")
        .addSubCommand('generate', 'generate new license')
            .addExecute((e) => {

                e.showModal(form);
            })
        .addSubCommand("get", "get all licences")
            .addExecute((e) => {

                e.reply('All licenses')
            })
        .addSubCommand("check", "check licence")
            .addOption('key', 'license key', true, 3)
            .addExecute((e) => {

                e.reply('Check')
            })
        .addSubCommand("delete", "delete license")
            .addOption('key', 'license key', true, 3)
            .addExecute((e) => {

                e.reply('Deleted!')
            })
        .build();

    const tokenCmd = new CmdBuilder("token","create new token")
        .addSubCommand("generate", "generate new token")
            .addOption("admin", "generate admin token", false, 5)
            .addExecute((e) => {
                const options = e.options;

                const adminOption = options.getBoolean('admin')
                const admin = adminOption === null ?  false : adminOption;

                console.log(admin);

                e.reply('Generated!')
            })
        .addSubCommand("delete", "delete token")
            .addOption('key', 'token key', true, 3)
            .addExecute((e) => {

                e.reply('Deleted!')
            })
        .addSubCommand("get", "get all tokens")
            .addExecute((e) => {

                e.reply('All tokens')
            })
        .build();


    botManager.registerCommands(tokenCmd, licenseCmd);
};

const init = (client) =>{
    const botManager = new BotManager(client);

    createForms(botManager);
    createCommands(botManager);
};

module.exports = { init }