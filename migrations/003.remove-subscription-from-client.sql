ALTER TABLE clients
    DROP COLUMN IF EXISTS subscription_id,
    DROP CONSTRAINT IF EXISTS fk_subscription