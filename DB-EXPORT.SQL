-- Adminer 4.8.1 PostgreSQL 15.2 (Debian 15.2-1.pgdg110+1) dump

\connect "mess";

DROP TABLE IF EXISTS "message";
DROP SEQUENCE IF EXISTS message_id_seq;
CREATE SEQUENCE message_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."message"
(
    "id"              bigint DEFAULT nextval('message_id_seq') NOT NULL,
    "message"         character varying(255),
    "recipient_names" character varying(255),
    "sender_name"     character varying(255),
    "subject"         character varying(255),
    "timestamp"       timestamptz(6),
    "sender_id"       bigint,
    CONSTRAINT "message_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "message_recipients";
CREATE TABLE "public"."message_recipients"
(
    "received_messages_id" bigint NOT NULL,
    "recipients_id"        bigint NOT NULL
) WITH (oids = false);


DROP TABLE IF EXISTS "user_account";
DROP SEQUENCE IF EXISTS user_account_id_seq;
CREATE SEQUENCE user_account_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."user_account"
(
    "id"       bigint DEFAULT nextval('user_account_id_seq') NOT NULL,
    "password" character varying(255),
    "username" character varying(255),
    CONSTRAINT "uk_castjbvpeeus0r8lbpehiu0e4" UNIQUE ("username"),
    CONSTRAINT "user_account_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


ALTER TABLE ONLY "public"."message"
    ADD CONSTRAINT "fkrdx8hgntft2s9ulc5px1d9rcp" FOREIGN KEY (sender_id) REFERENCES user_account (id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."message_recipients"
    ADD CONSTRAINT "fk8h2fd8uftul9vo3osm959wleg" FOREIGN KEY (received_messages_id) REFERENCES message (id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."message_recipients"
    ADD CONSTRAINT "fkct95g1ho5m29s88li78troks" FOREIGN KEY (recipients_id) REFERENCES user_account (id) NOT DEFERRABLE;

-- 2023-03-13 17:29:05.215598+00