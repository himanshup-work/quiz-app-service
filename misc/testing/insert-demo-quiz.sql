-- Insert Quiz Data
INSERT INTO quiz_app_service.quiz (quiz_id, quiz_title, category_id, quiz_description, time_limit, passing_score, created_by)
VALUES
('quiz1', 'Science Quiz', 1, 'A quiz to test your knowledge in science.', '00:30:00', 10, 'admin@himanshu'),
('quiz2', 'History Quiz', 2, 'A quiz to test your knowledge in history.', '00:30:00', 10, 'admin@himanshu');

-- Insert Question Data for Science Quiz
INSERT INTO quiz_app_service.question (question_id, question_text, quiz_id)
VALUES
('q1', 'What is the chemical symbol for water?', 'quiz1'),
('q2', 'What planet is known as the Red Planet?', 'quiz1'),
('q3', 'What is the largest organ in the human body?', 'quiz1'),
('q4', 'Which gas do plants absorb from the air for photosynthesis?', 'quiz1'),
('q5', 'What is the freezing point of water in Celsius?', 'quiz1'),
('q6', 'Which element is represented by the symbol O?', 'quiz1'),
('q7', 'What is the chemical formula for methane?', 'quiz1'),
('q8', 'What is the force that pulls objects towards Earth?', 'quiz1'),
('q9', 'How many bones are in the adult human body?', 'quiz1'),
('q10', 'Which organ is responsible for pumping blood?', 'quiz1'),
('q11', 'What is the chemical symbol for gold?', 'quiz1'),
('q12', 'What is the powerhouse of the cell?', 'quiz1'),
('q13', 'What is the speed of light?', 'quiz1'),
('q14', 'What is the most common gas in Earth’s atmosphere?', 'quiz1'),
('q15', 'What is the atomic number of carbon?', 'quiz1');

-- Insert Question Data for History Quiz
INSERT INTO quiz_app_service.question (question_id, question_text, quiz_id)
VALUES
('q16', 'Who was the first president of the United States?', 'quiz2'),
('q17', 'In which year did World War II end?', 'quiz2'),
('q18', 'Who wrote the Declaration of Independence?', 'quiz2'),
('q19', 'Which empire was ruled by Julius Caesar?', 'quiz2'),
('q20', 'What year did the Titanic sink?', 'quiz2'),
('q21', 'Who was the first woman to fly solo across the Atlantic?', 'quiz2'),
('q22', 'What civilization built the pyramids of Giza?', 'quiz2'),
('q23', 'Who was the last emperor of Rome?', 'quiz2'),
('q24', 'Which war was fought between the North and South regions of the United States?', 'quiz2'),
('q25', 'Who discovered America in 1492?', 'quiz2'),
('q26', 'Which country was formerly known as Persia?', 'quiz2'),
('q27', 'In which year did the Berlin Wall fall?', 'quiz2'),
('q28', 'Who was the leader of the Soviet Union during World War II?', 'quiz2'),
('q29', 'Who was the famous queen of Egypt known for her beauty and intelligence?', 'quiz2'),
('q30', 'What ancient city is considered the birthplace of democracy?', 'quiz2');

-- Insert Option Data for Science Quiz
INSERT INTO quiz_app_service.options (option_id, option_text, question_id, is_correct)
VALUES
-- Options for Question 1
('o1_1', 'H2O', 'q1', true),
('o1_2', 'CO2', 'q1', false),
('o1_3', 'O2', 'q1', false),
('o1_4', 'N2', 'q1', false),

-- Options for Question 2
('o2_1', 'Mars', 'q2', true),
('o2_2', 'Earth', 'q2', false),
('o2_3', 'Jupiter', 'q2', false),
('o2_4', 'Venus', 'q2', false),

-- Options for Question 3
('o3_1', 'Skin', 'q3', false),
('o3_2', 'Heart', 'q3', false),
('o3_3', 'Lungs', 'q3', false),
('o3_4', 'Liver', 'q3', true),

-- Options for Question 4
('o4_1', 'Oxygen', 'q4', false),
('o4_2', 'Carbon Dioxide', 'q4', true),
('o4_3', 'Nitrogen', 'q4', false),
('o4_4', 'Hydrogen', 'q4', false),

-- Options for Question 5
('o5_1', '0°C', 'q5', true),
('o5_2', '32°F', 'q5', true),
('o5_3', '50°C', 'q5', false),
('o5_4', '100°C', 'q5', false),

-- Options for Question 6
('o6_1', 'Hydrogen', 'q6', false),
('o6_2', 'Oxygen', 'q6', true),
('o6_3', 'Nitrogen', 'q6', false),
('o6_4', 'Carbon', 'q6', false),

-- Options for Question 7
('o7_1', 'CH4', 'q7', true),
('o7_2', 'CO2', 'q7', false),
('o7_3', 'H2O', 'q7', false),
('o7_4', 'N2', 'q7', false),

-- Options for Question 8
('o8_1', 'Gravity', 'q8', true),
('o8_2', 'Magnetism', 'q8', false),
('o8_3', 'Friction', 'q8', false),
('o8_4', 'Electricity', 'q8', false),

-- Options for Question 9
('o9_1', '206', 'q9', true),
('o9_2', '213', 'q9', false),
('o9_3', '150', 'q9', false),
('o9_4', '280', 'q9', false),

-- Options for Question 10
('o10_1', 'Brain', 'q10', false),
('o10_2', 'Heart', 'q10', true),
('o10_3', 'Stomach', 'q10', false),
('o10_4', 'Liver', 'q10', false),

-- Options for Question 11
('o11_1', 'Ag', 'q11', false),
('o11_2', 'Au', 'q11', true),
('o11_3', 'Al', 'q11', false),
('o11_4', 'Fe', 'q11', false),

-- Options for Question 12
('o12_1', 'Mitochondria', 'q12', true),
('o12_2', 'Nucleus', 'q12', false),
('o12_3', 'Chloroplast', 'q12', false),
('o12_4', 'Endoplasmic Reticulum', 'q12', false),

-- Options for Question 13
('o13_1', '300,000 km/s', 'q13', true),
('o13_2', '150,000 km/s', 'q13', false),
('o13_3', '450,000 km/s', 'q13', false),
('o13_4', '500,000 km/s', 'q13', false),

-- Options for Question 14
('o14_1', 'Oxygen', 'q14', false),
('o14_2', 'Carbon Dioxide', 'q14', false),
('o14_3', 'Nitrogen', 'q14', true),
('o14_4', 'Hydrogen', 'q14', false),

-- Options for Question 15
('o15_1', '6', 'q15', true),
('o15_2', '8', 'q15', false),
('o15_3', '10', 'q15', false),
('o15_4', '12', 'q15', false);

-- Insert Option Data for History Quiz
INSERT INTO quiz_app_service.options (option_id, option_text, question_id, is_correct)
VALUES
-- Options for Question 16
('o16_1', 'George Washington', 'q16', true),
('o16_2', 'Abraham Lincoln', 'q16', false),
('o16_3', 'Thomas Jefferson', 'q16', false),
('o16_4', 'John Adams', 'q16', false),

-- Options for Question 17
('o17_1', '1945', 'q17', true),
('o17_2', '1939', 'q17', false),
('o17_3', '1918', 'q17', false),
('o17_4', '1950', 'q17', false),

-- Options for Question 18
('o18_1', 'George Washington', 'q18', false),
('o18_2', 'Thomas Jefferson', 'q18', true),
('o18_3', 'Abraham Lincoln', 'q18', false),
('o18_4', 'Benjamin Franklin', 'q18', false),

-- Options for Question 19
('o19_1', 'Roman Empire', 'q19', true),
('o19_2', 'Ottoman Empire', 'q19', false),
('o19_3', 'Byzantine Empire', 'q19', false),
('o19_4', 'British Empire', 'q19', false),

-- Options for Question 20
('o20_1', '1910', 'q20', false),
('o20_2', '1912', 'q20', true),
('o20_3', '1908', 'q20', false),
('o20_4', '1915', 'q20', false),

-- Options for Question 21
('o21_1', 'Amelia Earhart', 'q21', true),
('o21_2', 'Eleanor Roosevelt', 'q21', false),
('o21_3', 'Clara Barton', 'q21', false),
('o21_4', 'Harriet Tubman', 'q21', false),

-- Options for Question 22
('o22_1', 'Mayan Civilization', 'q22', false),
('o22_2', 'Egyptian Civilization', 'q22', false),
('o22_3', 'Greek Civilization', 'q22', false),
('o22_4', 'Ancient Egyptian Civilization', 'q22', true),

-- Options for Question 23
('o23_1', 'Julius Caesar', 'q23', true),
('o23_2', 'Augustus', 'q23', false),
('o23_3', 'Nero', 'q23', false),
('o23_4', 'Caligula', 'q23', false),

-- Options for Question 24
('o24_1', 'World War I', 'q24', false),
('o24_2', 'Civil War', 'q24', true),
('o24_3', 'World War II', 'q24', false),
('o24_4', 'Vietnam War', 'q24', false),

-- Options for Question 25
('o25_1', 'Christopher Columbus', 'q25', false),
('o25_2', 'John Cabot', 'q25', false),
('o25_3', 'Marco Polo', 'q25', false),
('o25_4', 'Leif Erikson', 'q25', true),

-- Options for Question 26
('o26_1', 'Persia', 'q26', true),
('o26_2', 'India', 'q26', false),
('o26_3', 'Roman Empire', 'q26', false),
('o26_4', 'Egypt', 'q26', false),

-- Options for Question 27
('o27_1', '1990', 'q27', false),
('o27_2', '1989', 'q27', true),
('o27_3', '1987', 'q27', false),
('o27_4', '1992', 'q27', false),

-- Options for Question 28
('o28_1', 'Joseph Stalin', 'q28', false),
('o28_2', 'Vladimir Lenin', 'q28', true),
('o28_3', 'Leon Trotsky', 'q28', false),
('o28_4', 'Mikhail Gorbachev', 'q28', false),

-- Options for Question 29
('o29_1', 'Cleopatra', 'q29', true),
('o29_2', 'Nefertiti', 'q29', false),
('o29_3', 'Hatshepsut', 'q29', false),
('o29_4', 'Elizabeth I', 'q29', false),

-- Options for Question 30
('o30_1', 'Athens', 'q30', true),
('o30_2', 'Rome', 'q30', false),
('o30_3', 'Sparta', 'q30', false),
('o30_4', 'Cairo', 'q30', false);
