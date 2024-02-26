const { BotManager } = require("../utils/botutils");
const { FormBuilder } = require("../utils/formbuilder");

const { TextInputStyle } = require("discord.js");
const { CmdBuilder } = require("../utils/commandbuilder");

const { licenseForm } = require('../../settings/messages.json');
const { getReport, isValid, createToken, createLicense, deleteToken, deleteLicense } = require("../service/commandservice");

const { title, ownerLabel, descriptionLabel, timeLabel } = licenseForm;

let form;

const createForms = (botManager) =>{
    const formTemplate = new FormBuilder(title)
        .addTextInput('1', ownerLabel, TextInputStyle.Short, true)
        .addTextInput('2', descriptionLabel, TextInputStyle.Paragraph, false)
        .addTextInput('3', timeLabel, TextInputStyle.Short, false)
        .addExecute(async e => {
            const owner = e.fields.getTextInputValue('1');
            const description = e.fields.getTextInputValue('2');
            const time = e.fields.getTextInputValue('3');

            const message = await createLicense(owner, description, time);

            e.reply(message);
        })
        .build();

    form = botManager.createForm(formTemplate);
};

const createCommands = (botManager) =>{
    const licenseCmd = new CmdBuilder("license","create new license")
        .addSubCommand('generate', 'generate new license')
            .addExecute(async (e) => {
                e.showModal(form);
            })
        .addSubCommand("check", "check licence")
            .addOption('key', 'license key', true, 3)
            .addExecute(async (e) => {
                const options = e.options;
                const key = options.getString('key');

                const message = await isValid(key);

                e.reply(message);
            })
        .addSubCommand("delete", "delete license")
            .addOption('id', 'license id', true, 4)
            .addExecute(async (e) => {
                const options = e.options;
                const id = options.getInteger('id');

                const message = await deleteLicense(id);

                e.reply(message);
            })
        .build();

    const tokenCmd = new CmdBuilder("token","create new token")
        .addSubCommand("generate", "generate new token")
            .addOption("admin", "generate admin token", false, 5)
            .addExecute(async (e) => {
                const options = e.options;

                const adminOption = options.getBoolean('admin');
                const admin = adminOption === null ? false : adminOption;

                const message = await createToken(admin);

                e.reply(message);
            })
        .addSubCommand("delete", "delete token")
            .addOption('id', 'token id', true, 4)
            .addExecute(async (e) => {
                const options = e.options;
                const id = options.getInteger('id');

                const message = await deleteToken(id);

                e.reply(message);
            })
        .build();

    const reportCmd = new CmdBuilder("report","get all licenses and tokens")
        .addExecute(async (e) => {
            const message = await getReport();

            e.reply(message);
        })
        .build();

    botManager.registerCommands(tokenCmd, licenseCmd, reportCmd);
};

const init = (client) =>{
    const botManager = new BotManager(client);

    createForms(botManager);
    createCommands(botManager);
};

module.exports = { init }