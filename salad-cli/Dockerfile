FROM fabric8/java-centos-openjdk8-jdk

USER root
ADD app /app
RUN rpm -i /app/*.rpm

WORKDIR /app
ENV CUCUMBER_SALAD_HOME=/usr/share/cucumber-salad
RUN cucumber-salad --init
VOLUME /app/features
VOLUME /app/steps


CMD ["cucumber-salad"]