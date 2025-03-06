-- MySQL dump pro genesisresources
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `person_id` varchar(50) NOT NULL,
  `uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `person_id` (`person_id`),
  UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` VALUES
(2, 'Jane', 'Smith', 'P002', '6ba7b810-9dad-11d1-80b4-00c04fd430c8'),
(3, 'Alice', 'Johnson', 'P003', '7c4f14b0-8e7c-11e9-a848-0cc47a792c0a'),
(8, 'Jan', 'Novák', 'P004', '550e8400-e29b-41d4-a716-446655440001'),
(13, 'TestUser', 'TestSurname', 'P005', 'a87275c9-f5f6-4e2b-a95a-b9d076fe0a34'),
(14, 'Test', 'User', 'P12345', 'c1b2f952-a491-4c3a-81f6-013ccea76ad5'),
(15, 'NewUser', 'NewSurname', 'P006', '81eb8d95-b181-459c-a06f-d96da6ae3a39'),
(16, 'NewUser', 'NewSurname', 'P007', '4d16f507-c9c0-40 []

f0-8373-d27e5d344299');