const { GET, DELETE, POST} = require("./requestservice");
const { AttachmentBuilder } = require("discord.js");
const { createEmbed, MessageType} = require("../utils/embedutils");

const handleError = (error) => {
    const response = error.response;

    let message = 'Cannot connect to API!'
    let messageType = MessageType.ERROR;

    if (response) {
        message = response.data.message;
        messageType = MessageType.WARN;
    }

    return { content: '', ephemeral: true, embeds: [createEmbed(message, messageType)] };
};

const success = (message, file) => {
    if(file) return {content: '',ephemeral: true, embeds: [createEmbed(message, MessageType.SUCCESS)], files: [file]};

    return {content: '', ephemeral: true, embeds: [createEmbed(message, MessageType.SUCCESS)]};
};

const getReport = async ()=>{
    return GET('report/generate/excel', 'arraybuffer')
        .then((response) => {
            const buffer = Buffer.from(response.data);
            const attachment = new AttachmentBuilder(buffer, 'binary')
                .setName('report.xlsx');

            return success('Report generated', attachment);
        }).catch(err => handleError(err));
}

const isValid = async (key)=>{
    return GET(`license/isValid/${key}`,'json')
        .then((response) => {

            return success(`License **${key}** is valid`);
        }).catch(err => handleError(err));
}

const createLicense = async (owner, description, time)=>{

    if (time && isNaN(time)) return { embeds: [createEmbed("Time must be number!", MessageType.WARN)] };

    const expireDays = Number(time);

    const data = {
        responseType: 'json',
        owner,
        description,
        expireDays
    }

    return POST('license/save', data).then((response) => {
        const data = response.data;

        const key = data.licenseKey;
        const owner = data.owner;

        const expire = getExpireDate(data.expirationDate)

        return success(`Owner: **${owner}** \nExpire: **${expire}** \n\nToken: \`\`\`${key}\`\`\``);
    }).catch(err => handleError(err));
}

const getExpireDate = (expirationDate) => {
    let expire = 'never';

    if(expirationDate > 0) {
        let date = new Date(expirationDate);

        let optionsTime = {hour: '2-digit', minute: '2-digit'};

        let day = date.getDate().toString().padStart(2, '0');
        let month = (date.getMonth() + 1).toString().padStart(2, '0');
        let year = date.getFullYear();

        let dateString = `${day}/${month}/${year}`;

        let timeString = date.toLocaleTimeString(undefined, optionsTime);

        expire = timeString + " - " + dateString;
    }
    return expire;
}

const createToken = async (isAdmin)=>{
    const tokenType = isAdmin ? 'ADMIN': 'DEFAULT';

    const data = {
        responseType: 'json'
    }

    return POST(`token/create/${tokenType}`, data).then((response) => {
        const data = response.data;

        const token = data.accessToken;
        const type = data.tokenType;

        return success(`Generated **${type}** token: \`\`\`${token}\`\`\``);
    }).catch(err => handleError(err));
}

const deleteToken = async (id)=>{
    return DELETE(`token/delete/${id}`,'json')
        .then((response) => {

            return success(`Deleted token with id **${id}**`);
        }).catch(err => handleError(err));
}

const deleteLicense = async (id)=>{
    return DELETE(`license/delete/${id}`,'json')
        .then((response) => {

            return success(`Deleted license with id **${id}**`);
        }).catch(err => handleError(err));
}

module.exports = { getReport, isValid, createToken, createLicense, deleteToken, deleteLicense };