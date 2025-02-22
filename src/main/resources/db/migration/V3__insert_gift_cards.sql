INSERT INTO gift_cards (id, code, amount, name, description, company, created_at, expires_at)
VALUES
('1', 'GC-12345XYZ', 50.00, 'Amazon Gift Card', 'Tarjeta de regalo para compras en Amazon.', 'Amazon', NOW(), NOW() + INTERVAL '6 months'),

('2', 'GC-67890ABC', 100.00, 'Spotify Premium', 'Suscripción de 6 meses a Spotify Premium.', 'Spotify', NOW(), NOW() + INTERVAL '6 months'),

('3', 'GC-54321LMN', 75.50, 'Netflix Gift Card', 'Tarjeta de regalo para Netflix, ideal para ver series y películas.', 'Netflix', NOW(), NOW() + INTERVAL '6 months'),

('4', 'GC-98765PQR', 25.00, 'Steam Wallet', 'Saldo para comprar juegos y contenido en Steam.', 'Steam', NOW(), NOW() + INTERVAL '6 months'),

('5', 'GC-24680XYZ', 150.00, 'Google Play Gift Card', 'Saldo en Google Play para comprar apps, juegos, y más.', 'Google', NOW(), NOW() + INTERVAL '6 months'),

('6', 'GC-13579ABC', 200.00, 'Apple Gift Card', 'Saldo para App Store y productos Apple.', 'Apple', NOW(), NOW() + INTERVAL '6 months');
