insert into users (username, password, enabled, authority_id)
values ('root', '$2a$10$crb3lFTJ6HOauGOi/vTq4ORmT7zpu/Gl6QcRlsko9RCkPUN5UIiBm', true,
        (select id from authorities where authority = 'ROLE_ADMIN'));