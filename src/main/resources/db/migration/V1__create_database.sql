DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'giftcarddb') THEN
      CREATE DATABASE giftcarddb;
   END IF;
END $$;