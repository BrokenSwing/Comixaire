ALTER TABLE subscriptions
    ADD COLUMN client_id INT,
    ADD CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES clients (client_id)
        ON DELETE CASCADE


