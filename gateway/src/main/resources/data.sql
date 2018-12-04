INSERT INTO SERVER (id, name, uri) VALUES (1, 'zuul-gateway', 'http://localhost:8085');
INSERT INTO USER VALUES (1, '$2a$10$VfnpmiA5eeMcNkzpqAIoFOV8ADSPx6zQj2UJZBydaAteYlYXB02by', 'brunno');
INSERT INTO USER VALUES (2, '$2a$10$VfnpmiA5eeMcNkzpqAIoFOV8ADSPx6zQj2UJZBydaAteYlYXB02by', 'test');

INSERT INTO RESOURCE (id, service, path, method) VALUES (1, 'servers', '/', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (2, 'servers', '/', 'POST');
INSERT INTO RESOURCE (id, service, path, method) VALUES (3, 'servers', '/*', 'PUT');
INSERT INTO RESOURCE (id, service, path, method) VALUES (4, 'servers', '/*', 'DELETE');
INSERT INTO RESOURCE (id, service, path, method) VALUES (5, 'servers', '/*', 'GET');

INSERT INTO RESOURCE (id, service, path, method) VALUES (6, 'users', '/', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (7, 'users', '/', 'POST');
INSERT INTO RESOURCE (id, service, path, method) VALUES (8, 'users', '/*', 'PUT');
INSERT INTO RESOURCE (id, service, path, method) VALUES (9, 'users', '/*', 'DELETE');
INSERT INTO RESOURCE (id, service, path, method) VALUES (10, 'users', '/*', 'GET');

INSERT INTO RESOURCE (id, service, path, method) VALUES (11, 'authorities', '/', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (12, 'authorities', '/', 'POST');
INSERT INTO RESOURCE (id, service, path, method) VALUES (13, 'authorities', '/*', 'PUT');
INSERT INTO RESOURCE (id, service, path, method) VALUES (14, 'authorities', '/*', 'DELETE');
INSERT INTO RESOURCE (id, service, path, method) VALUES (15, 'authorities', '/*', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (16, 'authorities', '/user/*', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (17, 'authorities', '/services', 'GET');

INSERT INTO RESOURCE (id, service, path, method) VALUES (18, 'resources', '/', 'GET');
INSERT INTO RESOURCE (id, service, path, method) VALUES (19, 'resources', '/', 'POST');
INSERT INTO RESOURCE (id, service, path, method) VALUES (20, 'resources', '/*', 'PUT');
INSERT INTO RESOURCE (id, service, path, method) VALUES (21, 'resources', '/*', 'DELETE');
INSERT INTO RESOURCE (id, service, path, method) VALUES (22, 'resources', '/*', 'GET');

INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 1, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 2, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 3, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 4, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 5, 1);

INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 6, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 7, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 8, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 9, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 10, 1);

INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 11, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 12, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 13, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 14, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 15, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 16, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 17, 1);

INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 18, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 19, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 20, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 21, 1);
INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 22, 1);

INSERT INTO AUTHORITY (server_id, resource_id, user_id) VALUES (1, 17, 2);
