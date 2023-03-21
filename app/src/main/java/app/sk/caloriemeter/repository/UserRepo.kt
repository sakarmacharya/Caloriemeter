package app.sk.caloriemeter.repository

import app.sk.caloriemeter.db.User
import app.sk.caloriemeter.db.UserDao

class UserRepo(private val userDao: UserDao) {
    val user: User = userDao.getUser()
}