class CmdBuilder {
    constructor(name, description) {
        this.name = name;
        this.description = description;
        this.options = [];
    }

    addOption(name, description, required, type) {
        this.options.push({name, description, required, type});
        return this;
    }
    addExecute(execute){
        this.execute = execute;
        return this;
    }

    build() {
        return new CommandTemplate(this.name, this.description, this.options, this.execute)
    }
}

class CommandTemplate {
    constructor(name, description, options, execute) {
        this.name = name;
        this.description = description;
        this.execute = execute;
        this.options = options;
    }
}

module.exports = { CmdBuilder, CommandTemplate }