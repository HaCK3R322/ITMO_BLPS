-- Insert sample data into Criminal table
INSERT INTO criminals (id, name, surname, patronymic, date_of_birth, city)
VALUES
    (1, 'Иван', 'Андросов', 'Сергеевич', '2002-05-08', 'SPB'),
    (2, 'Артем', 'Андросов', 'Сергеевич', '2005-11-17', 'SPB');

-- Insert sample data into CriminalPhone table
INSERT INTO criminal_phones (id, number)
VALUES
    (1, '9013161116'),
    (2, '9013163311');