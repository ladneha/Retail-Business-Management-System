drop sequence pur#_seq;
drop sequence sup#_seq;
drop sequence log#_seq;


create sequence pur#_seq
increment by 1
start with 100015
order;

create sequence sup#_seq
increment by 1
start with 1010 maxvalue 9999
order
cycle;

create sequence log#_seq
increment by 2
start with 10000 maxvalue 99999
order;
