const crypto= require('crypto');
const { TextInputStyle } = require("discord.js");
class FormBuilder {

    /**
     * @param {string} title
     */
    constructor(title) {
        this.id = crypto.randomUUID();
        this.title = title;
        this.inputs = [];
    }
    /**
     * Add text input
     * @param {string} id
     * @param {string} label
     * @param {TextInputStyle} style
     * @param {boolean} required
     */
    addTextInput(id, label, style, required) {
        this.inputs.push({id, label, style, required});
        return this;
    }

    /**
     * Add execute function to form
     * @param {function} execute
     */
    addExecute(execute){
        this.execute = execute;
        return this;
    }

    build() {
        return new FormTemplate(this.id, this.title, this.inputs, this.execute);
    }
}

class FormTemplate {
    constructor(id, title, inputs, execute) {
        this.id = id;
        this.title = title;
        this.inputs = inputs;
        this.execute = execute;
    }
}

module.exports = { FormBuilder, FormTemplate }