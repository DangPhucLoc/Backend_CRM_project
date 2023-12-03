
	
INSERT INTO roles( name, description ) VALUES ("ROLE_USER3", "Nhân viên4");

insert into roles(name, description ) values ( "roleTest","test");
SELECT * FROM crm_app.status;


update users 
set email = "phucloc20" , password = "1111", fullname = "phucloc", 
avatar = "loc", role_id = 2 where id = 1