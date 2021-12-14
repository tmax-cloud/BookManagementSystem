FROM postgres

VOLUME /var/lib/postgresql/data

COPY ./make/db/docker-entrypoint.sh /docker-entrypoint.sh
COPY ./make/db/initdb.sh /initdb.sh
COPY ./make/db/initial-core.sql /docker-entrypoint-initdb.d/
COPY ./make/db/initial-rating.sql /docker-entrypoint-initdb.d/
COPY ./make/db/initial-rating-v2.sql /docker-entrypoint-initdb.d/
COPY ./make/db/initial-order.sql /docker-entrypoint-initdb.d/
COPY ./make/db/initial-payment.sql /docker-entrypoint-initdb.d/
COPY ./make/db/docker-healthcheck.sh /docker-healthcheck.sh
RUN chown -R postgres:postgres /docker-entrypoint.sh /docker-healthcheck.sh /docker-entrypoint-initdb.d \
    && chmod u+x /docker-entrypoint.sh /docker-healthcheck.sh

ENTRYPOINT ["/docker-entrypoint.sh", "96", "13"]
HEALTHCHECK CMD ["/docker-healthcheck.sh"]

USER postgres
