//package app;
//
//import app.command.Create;
//import app.command.Delete;
//import app.command.ListAll;
//import app.service.NoteService;
//
//public class Main {
//
//    public static void main(String[] args) {
//        var service = new NoteService();
//
//        service.handle(new Create("Hello", "World"));
//        service.handle(new ListAll());
//        service.handle(new Delete(1));
//        service.handle(new ListAll());
//    }
//}
