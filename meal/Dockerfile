FROM oracle/graalvm-ce:19.0.0 as graalvm
COPY . /home/app/backend
WORKDIR /home/app/backend
RUN gu install native-image
RUN native-image --no-server -cp build/libs/backend-*-all.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/backend .
ENTRYPOINT ["./backend"]
