const { Events, ModalBuilder, TextInputBuilder, ActionRowBuilder, ButtonBuilder} = require("discord.js");

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
            }else if (interaction.isCommand()) {
                this.handleCommand(interaction);
            }
        });
    }

    handleCommand(e) {
        const command = this.commands.find((cmd) => cmd.name === e.commandName);

        if (command){
            const options = command.options;
            const sCmd = e.options._subcommand;

            if(!sCmd){
                command.execute(e);
            }else{
                const subcommand = options.find(sub => sub.name === sCmd);
                if(subcommand) subcommand.execute(e);
            }
        }
    }

    handleForm(e) {
        const form = this.forms.find((form) => form.id === e.customId);

        if (form) form.execute(e);
    }

    registerCommands(...commands){
        commands.forEach(cmd => this.registerCommand(cmd));
    }

    registerCommand(cmd) {
        let options = cmd.subcommands;

        if(cmd.subcommands.length === 0) options = cmd.options

        const command = {
            name: cmd.name,
            description: cmd.description,
            options: options || [],
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
                .setRequired(input.required)
                .setLabel(input.label)
                .setStyle(input.style);

            comps.push(new ActionRowBuilder().addComponents(genInput));
        });

        modal.addComponents(comps);

        this.forms.push(form);

        return modal;
    }
}

module.exports = { BotManager }