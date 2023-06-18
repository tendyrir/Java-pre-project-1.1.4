package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl usi = new UserServiceImpl();

//        Создание таблицы User(ов)
        usi.createUsersTable();
//        Добавление 4 User(ов) в таблицу с данными на свой выбор.
//        После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        usi.saveUser("Stepan", "Shatalov", (byte) 23);
        usi.saveUser("Ivan", "Efimov", (byte) 24);
        usi.saveUser("Natasha", "Vasenkova", (byte) 26);
        usi.saveUser("Maxim", "Kuznetsov", (byte) 25);
//        Получение всех User из базы и вывод в консоль (должен быть переопределен toString в классе User)
        List<User> userList = usi.getAllUsers();
        for (User user: userList) {
            System.out.println(user.toString());
        }
//        Очистка таблицы User(ов)
        usi.cleanUsersTable();
//        Удаление таблицы
        usi.dropUsersTable();
    }
}
