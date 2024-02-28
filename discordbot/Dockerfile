FROM node:20

WORKDIR /bot

COPY package.json yarn.lock ./

RUN yarn install

COPY . .

CMD ["node", "src/bot.js"]
