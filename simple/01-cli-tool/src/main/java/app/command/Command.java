package app.command;

// step 1 - 도메인 타입부터 작성
public sealed interface Command permits Create, ListAll, Delete{}
