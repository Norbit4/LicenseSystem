const { Events, ModalBuilder, TextInputBuilder, ActionRowBuilder } = require("discord.js");

class BotManager {
    constructor(client) {
        this.client = client;
        this.forms = [];
        this.commands = [];
        this.listener();
    }

    listener() {
        this.client.on(Events.InteractionCreate, async (interaction) => {
            if(interaction.isModalSubmit()){
                this.handleForm(interaction);
            } else if (interaction.isCommand()){
                this.handleCommand(interaction);
            }
        });
    }

    handleCommand(e) {
        const command = this.commands.find((cmd) => cmd.name === e.commandName);

        if (command) command.execute(e);
    }

    handleForm(e) {
        const form = this.forms.find((form) => form.id === e.customId);

        if (form) form.execute(e);
    }

    registerCommand(cmd) {
        const command = {
            name: cmd.name,
            description: cmd.description,
            options: cmd.options || [],
            execute: cmd.execute || (() => {}),
        };

        this.commands.push(command);

        this.client.guilds.cache.forEach((guild) => {
            guild.commands.create(command);
        });
    }

    createForm(form) {
        const modal = new ModalBuilder()
            .setCustomId(form.id)
            .setTitle(form.title);

        const comps = []

        form.inputs.forEach(input =>{
            const genInput = new TextInputBuilder()
                .setCustomId(input.id)
                .setLabel(input.label)
                .setStyle(input.style);

            comps.push(new ActionRowBuilder().addComponents(genInput))
        });

        modal.addComponents(comps)

        this.forms.push(form)

        return modal;
    }
}

module.exports = { BotManager }