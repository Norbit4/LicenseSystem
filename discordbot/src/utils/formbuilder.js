const crypto= require('crypto');
class FormBuilder {
    constructor(title) {
        this.id = crypto.randomUUID();
        this.title = title;
        this.inputs = [];
    }

    addTextInput(id, label, style) {
        this.inputs.push({id, label, style});
        return this;
    }
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