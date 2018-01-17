insert into user (userId, userName, password, active, createBy, createDate, lastUpdate, lastUpdatedBy)
values(1, 'user', 'user', 1, 'user', sysdate(), sysdate(), 'user');


insert into country values (1,'Spain', sysdate(), 'user', sysdate(), 'user');
insert into country values (2,'United States', sysdate(), 'user', sysdate(), 'user');


insert into city values(1, 'Madrid', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(2, 'Barcelona', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(3, 'Valencia', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(4, 'Sevilla', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(5, 'Zaragoza', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(6, 'Malaga', 1, sysdate(), 'user', sysdate(), 'user');
insert into city values(7, 'Burgos', 1, sysdate(), 'user', sysdate(), 'user');

insert into city values(8, 'New York', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(9, 'Los Angeles', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(10, 'San Francisco', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(11, 'Chicago', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(12, 'Dallas', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(13, 'Miami', 2, sysdate(), 'user', sysdate(), 'user');
insert into city values(14, 'Houston', 2, sysdate(), 'user', sysdate(), 'user');


insert into address values(1, 'Address1', 'Address2', 1, '28080', '678876567', sysdate(), 'user', sysdate(), 'user');

insert into customer values(1, 'Ashoka Tano', 1, true, sysdate(), 'user', sysdate(), 'user');


insert into appointment VALUES
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

insert into appointment VALUES
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