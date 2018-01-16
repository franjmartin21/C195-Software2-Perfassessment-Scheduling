insert into Country values (1,'Spain', sysdate(), 'user', sysdate(), 'user');
insert into Country values (2,'United States', sysdate(), 'user', sysdate(), 'user');


insert into City values(1, 'Madrid', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(2, 'Barcelona', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(3, 'Valencia', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(4, 'Sevilla', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(5, 'Zaragoza', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(6, 'Malaga', 1, sysdate(), 'user', sysdate(), 'user');
insert into City values(7, 'Burgos', 1, sysdate(), 'user', sysdate(), 'user');

insert into City values(8, 'New York', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(9, 'Los Angeles', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(10, 'San Francisco', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(11, 'Chicago', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(12, 'Dallas', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(13, 'Miami', 2, sysdate(), 'user', sysdate(), 'user');
insert into City values(14, 'Houston', 2, sysdate(), 'user', sysdate(), 'user');


insert into Address values(1, 'Address1', 'Address2', 1, '28080', '678876567', sysdate(), 'user', sysdate(), 'user');

insert into Customer values(1, 'Ashoka Tano', 1, true, sysdate(), 'user', sysdate(), 'user');

insert into Appointment VALUES
  (
      1,
      1,
      'Reunion Importante',
      'INITIAL_CONSULTATION',
      'PHOENIX',
      '365365',
      'www.google.com',
      sysdate(),
      sysdate(),
      sysdate(),
      'user',
      sysdate(),
      'user');
  );

insert into Appointment VALUES
    (
        2,
        1,
        'Segunda Reunion',
        'INITIAL_CONSULTATION',
        'PHOENIX',
        '365365',
        'www.google.com',
        sysdate(),
        sysdate(),
        sysdate(),
        'user',
        sysdate(),
        'user');
);