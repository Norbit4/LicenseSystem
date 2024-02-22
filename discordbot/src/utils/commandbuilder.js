class CmdBuilder {

    /**
     * @param {string} name
     * @param {string} description
     */
    constructor(name, description) {
        this.name = name;
        this.description = description;
        this.options = [];
        this.subcommands = [];
    }

    /**
     * Add option to command or subcommand
     * @param {string} name
     * @param {string} description
     * @param {boolean} required - is option required.
     * @param {number} type - option type  [ 3 - String, 4 - Number, 5 - Boolean]
     */
    addOption(name, description, required, type) {
        if(type === 1) return this;

        const size = this.subcommands.length

        if(size === 0){
            this.options.push({name, description, required, type});
            return this;
        }

        const subCommand = this.subcommands.at(size - 1)

        subCommand.options.push({name, description, required, type});
        return this;
    }

    /**
     * Add subcommand to command
     * @param {string} name
     * @param {string} description
     */
    addSubCommand(name, description){
        const subcommand = {
            name,
            description,
            type: 1,
            options: []
        };
        this.subcommands.push(subcommand)
        return this;
    }

    /**
     * Add execute function to command or subcommand
     * @param {function} execute
     */
    addExecute(execute){
        const size = this.subcommands.length

        if(size === 0){
            this.execute = execute;
            return this;
        }
        const subCommand = this.subcommands.at(size - 1);
        subCommand.execute = execute;
        return this;
    }

    build() {
        return new CommandTemplate(this.name, this.description, this.options, this.subcommands, this.execute);
    }
}

class CommandTemplate {
    constructor(name, description, options, subcommands, execute) {
        this.name = name;
        this.description = description;
        this.execute = execute;
        this.options = options;
        this.subcommands = subcommands;
    }
}

module.exports = { CmdBuilder, CommandTemplate }