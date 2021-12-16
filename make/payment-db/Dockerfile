FROM postgres

VOLUME /var/lib/postgresql/data

COPY ./make/payment-db/docker-entrypoint.sh /docker-entrypoint.sh
COPY ./make/payment-db/initdb.sh /initdb.sh
COPY ./make/payment-db/initial-payment.sql /docker-entrypoint-initdb.d/
COPY ./make/payment-db/docker-healthcheck.sh /docker-healthcheck.sh
RUN chown -R postgres:postgres /docker-entrypoint.sh /docker-healthcheck.sh /docker-entrypoint-initdb.d \
    && chmod u+x /docker-entrypoint.sh /docker-healthcheck.sh

ENTRYPOINT ["/docker-entrypoint.sh", "96", "13"]
HEALTHCHECK CMD ["/docker-healthcheck.sh"]

USER postgres
