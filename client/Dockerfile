FROM node:lts as dev

WORKDIR client
COPY package.json /client/package.json
COPY package-lock.json /client/package-lock.json
RUN npm ci
COPY . /client
ENV PORT=3000

FROM dev as build
RUN ["npm", "run", "build"]

FROM nginx:alpine
#COPY ./.nginx/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /client/build /usr/share/nginx/html
ENTRYPOINT ["nginx", "-g", "daemon off;"]