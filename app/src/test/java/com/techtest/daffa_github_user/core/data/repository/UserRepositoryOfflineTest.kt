package com.techtest.daffa_github_user.core.data.repository

import com.techtest.daffa_github_user.core.data.local.LocalDataSource
import com.techtest.daffa_github_user.core.data.local.room.dao.UserDao
import com.techtest.daffa_github_user.core.data.local.room.dao.UserDetailDao
import com.techtest.daffa_github_user.core.data.local.room.entity.UserDetailEntity
import com.techtest.daffa_github_user.core.data.local.room.entity.UserEntity
import com.techtest.daffa_github_user.core.data.mapper.DataMapper
import com.techtest.daffa_github_user.core.data.remote.RemoteDataSource
import com.techtest.daffa_github_user.core.data.remote.network.ApiService
import com.techtest.daffa_github_user.core.data.remote.response.ListUserResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserDetailResponse
import com.techtest.daffa_github_user.core.data.remote.response.UserResponse
import com.techtest.daffa_github_user.core.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserRepositoryOfflineTest {
    @Test
    fun `empty search result is cached without displaying sentinel user`() = runBlocking {
        var requests = 0
        val api = object : ApiService {
            override suspend fun getListUser(token: String) = emptyList<UserResponse>()
            override suspend fun searchUser(username: String, token: String): ListUserResponse {
                requests++
                return ListUserResponse(emptyList())
            }
            override suspend fun getDetail(username: String, token: String) =
                UserDetailResponse(null, 0, null, 0, null, null, 1)
            override suspend fun getFollowers(username: String, token: String) = emptyList<UserResponse>()
            override suspend fun getFollowings(username: String, token: String) = emptyList<UserResponse>()
        }
        val rows = mutableListOf<UserEntity>()
        val dao = object : UserDao {
            override suspend fun insertListUser(user: List<UserEntity>) { rows.addAll(user) }
            override suspend fun insertUser(user: UserEntity) { rows.add(user) }
            override fun getListUser(): Flow<List<UserEntity>> = flowOf(rows.filter { it.cacheKey.startsWith("home:") })
            override fun getCachedList(prefix: String): Flow<List<UserEntity>> =
                flowOf(rows.filter { it.cacheKey.startsWith(prefix) })
            override suspend fun deleteCachedList(prefix: String) { rows.removeAll { it.cacheKey.startsWith(prefix) } }
            override suspend fun deleteHomeUsers() { rows.removeAll { it.cacheKey.startsWith("home:") } }
            override suspend fun replaceHomeUsers(users: List<UserEntity>) {
                deleteHomeUsers()
                insertListUser(users)
            }
            override suspend fun replaceCachedList(prefix: String, users: List<UserEntity>) {
                deleteCachedList(prefix)
                insertListUser(users)
            }
        }
        val local = LocalDataSource(dao, object : UserDetailDao {
            override suspend fun insertUser(user: UserDetailEntity) = Unit
            override fun getUser(username: String): Flow<UserDetailEntity?> = flowOf(null)
        })
        val repository = UserRepository(RemoteDataSource(api), local, DataMapper())

        val first = repository.searchUser("nobody").toList().last()
        val second = repository.searchUser("nobody").toList().last()

        assertEquals(emptyList<com.techtest.daffa_github_user.core.model.User>(), first.data)
        assertEquals(emptyList<com.techtest.daffa_github_user.core.model.User>(), second.data)
        assertEquals(1, requests)
    }

    @Test
    fun `empty home result is cached without another request`() = runBlocking {
        var requests = 0
        val api = object : ApiService {
            override suspend fun getListUser(token: String): List<UserResponse> {
                requests++
                return emptyList()
            }
            override suspend fun searchUser(username: String, token: String) = ListUserResponse(emptyList())
            override suspend fun getDetail(username: String, token: String) =
                UserDetailResponse(null, 0, null, 0, null, null, 1)
            override suspend fun getFollowers(username: String, token: String) = emptyList<UserResponse>()
            override suspend fun getFollowings(username: String, token: String) = emptyList<UserResponse>()
        }
        val rows = mutableListOf<UserEntity>()
        val dao = object : UserDao {
            override suspend fun insertListUser(user: List<UserEntity>) { rows.addAll(user) }
            override suspend fun insertUser(user: UserEntity) { rows.add(user) }
            override fun getListUser(): Flow<List<UserEntity>> = flowOf(rows.filter { it.cacheKey.startsWith("home:") })
            override fun getCachedList(prefix: String): Flow<List<UserEntity>> =
                flowOf(rows.filter { it.cacheKey.startsWith(prefix) })
            override suspend fun deleteCachedList(prefix: String) { rows.removeAll { it.cacheKey.startsWith(prefix) } }
            override suspend fun deleteHomeUsers() { rows.removeAll { it.cacheKey.startsWith("home:") } }
            override suspend fun replaceHomeUsers(users: List<UserEntity>) {
                deleteHomeUsers()
                insertListUser(users)
            }
            override suspend fun replaceCachedList(prefix: String, users: List<UserEntity>) {
                deleteCachedList(prefix)
                insertListUser(users)
            }
        }
        val local = LocalDataSource(dao, object : UserDetailDao {
            override suspend fun insertUser(user: UserDetailEntity) = Unit
            override fun getUser(username: String): Flow<UserDetailEntity?> = flowOf(null)
        })
        val repository = UserRepository(RemoteDataSource(api), local, DataMapper())

        val first = repository.getListUser().toList().last()
        val second = repository.getListUser().toList().last()

        assertEquals(emptyList<com.techtest.daffa_github_user.core.model.User>(), first.data)
        assertEquals(emptyList<com.techtest.daffa_github_user.core.model.User>(), second.data)
        assertEquals(1, requests)
    }

    @Test
    fun `search returns cached users without requesting network`() = runBlocking {
        var requests = 0
        val api = object : ApiService {
            override suspend fun getListUser(token: String) = emptyList<UserResponse>()
            override suspend fun searchUser(username: String, token: String): ListUserResponse {
                requests++
                throw AssertionError("network should not be requested")
            }
            override suspend fun getDetail(username: String, token: String) =
                UserDetailResponse(null, 0, null, 0, null, null, 1)
            override suspend fun getFollowers(username: String, token: String) = emptyList<UserResponse>()
            override suspend fun getFollowings(username: String, token: String) = emptyList<UserResponse>()
        }
        val userDao = object : UserDao {
            private val rows = mutableListOf(UserEntity("search:7:octocat:user:octocat", "octocat", "avatar"))
            override suspend fun insertListUser(user: List<UserEntity>) { rows.addAll(user) }
            override suspend fun insertUser(user: UserEntity) { rows.add(user) }
            override fun getListUser(): Flow<List<UserEntity>> = flowOf(emptyList())
            override fun getCachedList(prefix: String): Flow<List<UserEntity>> =
                flowOf(rows.filter { it.cacheKey.startsWith(prefix) })
            override suspend fun deleteCachedList(prefix: String) { rows.removeAll { it.cacheKey.startsWith(prefix) } }
            override suspend fun deleteHomeUsers() { rows.removeAll { it.cacheKey.startsWith("home:") } }
        }
        val local = LocalDataSource(userDao, object : UserDetailDao {
            override suspend fun insertUser(user: UserDetailEntity) = Unit
            override fun getUser(username: String): Flow<UserDetailEntity?> = flowOf(null)
        })

        val result = UserRepository(RemoteDataSource(api), local, DataMapper())
            .searchUser("octocat").toList().last()

        assertEquals(listOf(com.techtest.daffa_github_user.core.model.User("octocat", "avatar")), result.data)
        assertEquals(0, requests)
    }

    @Test
    fun `replacing home cache removes users omitted by latest response`() = runBlocking {
        val rows = mutableListOf(UserEntity("home:old", "old", "old-avatar"))
        val userDao = object : UserDao {
            override suspend fun insertListUser(user: List<UserEntity>) { rows.addAll(user) }
            override suspend fun insertUser(user: UserEntity) { rows.add(user) }
            override fun getListUser(): Flow<List<UserEntity>> = flowOf(rows.filter { it.cacheKey.startsWith("home:") })
            override fun getCachedList(prefix: String): Flow<List<UserEntity>> = flowOf(emptyList())
            override suspend fun deleteCachedList(prefix: String) = Unit
            override suspend fun deleteHomeUsers() { rows.removeAll { it.cacheKey.startsWith("home:") } }
        }
        val local = LocalDataSource(userDao, object : UserDetailDao {
            override suspend fun insertUser(user: UserDetailEntity) = Unit
            override fun getUser(username: String): Flow<UserDetailEntity?> = flowOf(null)
        })

        local.replaceHomeUsers(listOf(UserEntity("home:new", "new", "new-avatar")))

        assertEquals(listOf("new"), local.getListUser().toList().single().map { it.username })
    }

    @Test
    fun `detail returns cached record without requesting network`() = runBlocking {
        var requests = 0
        val api = object : ApiService {
            override suspend fun getListUser(token: String) = emptyList<UserResponse>()
            override suspend fun searchUser(username: String, token: String) = ListUserResponse(emptyList())
            override suspend fun getDetail(username: String, token: String): UserDetailResponse {
                requests++
                throw AssertionError("network should not be requested")
            }
            override suspend fun getFollowers(username: String, token: String) = emptyList<UserResponse>()
            override suspend fun getFollowings(username: String, token: String) = emptyList<UserResponse>()
        }
        val local = LocalDataSource(
            object : UserDao {
                override suspend fun insertListUser(user: List<UserEntity>) = Unit
                override suspend fun insertUser(user: UserEntity) = Unit
                override fun getListUser(): Flow<List<UserEntity>> = flowOf(emptyList())
                override fun getCachedList(prefix: String): Flow<List<UserEntity>> = flowOf(emptyList())
                override suspend fun deleteCachedList(prefix: String) = Unit
                override suspend fun deleteHomeUsers() = Unit
            },
            object : UserDetailDao {
                override suspend fun insertUser(user: UserDetailEntity) = Unit
                override fun getUser(username: String): Flow<UserDetailEntity?> = flowOf(
                    UserDetailEntity("octocat", "GitHub", "San Francisco", "avatar", 12, 4, "The Octocat")
                )
            }
        )
        val repository = UserRepository(RemoteDataSource(api), local, DataMapper())

        val result = repository.getDetailUser("octocat").toList().last()

        assertTrue(result is Resource.Success)
        val detail = (result as Resource.Success).data!!
        assertEquals("The Octocat", detail.name)
        assertEquals("GitHub", detail.company)
        assertEquals(0, requests)
    }
}
