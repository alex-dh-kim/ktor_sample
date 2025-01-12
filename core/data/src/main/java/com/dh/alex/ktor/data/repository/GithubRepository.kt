package com.dh.alex.ktor.data.repository

import com.dh.alex.ktor.common.alias.DHResult
import com.dh.alex.ktor.model.github.GithubRepo
import com.dh.alex.ktor.model.github.Owner
import com.dh.alex.ktor.network.api.ApiService
import com.dh.alex.ktor.network.model.response.github.GithubRepoResponse
import javax.inject.Inject

interface GithubRepository {
    suspend fun getRepositories(): DHResult<List<GithubRepo>>
}

internal class GithubRepositoryImpl @Inject constructor(
    private val api: ApiService
) : GithubRepository {
    override suspend fun getRepositories(): DHResult<List<GithubRepo>> {
        return api.getRepositories().map { it.map { it.transform() } }
    }
}

private fun GithubRepoResponse.transform(): GithubRepo {
    return GithubRepo(
        archiveUrl = archiveUrl ?: "",
        assigneesUrl = assigneesUrl ?: "",
        blobsUrl = blobsUrl ?: "",
        branchesUrl = branchesUrl ?: "",
        collaboratorsUrl = collaboratorsUrl ?: "",
        commentsUrl = commentsUrl ?: "",
        commitsUrl = commitsUrl ?: "",
        compareUrl = compareUrl ?: "",
        contentsUrl = contentsUrl ?: "",
        contributorsUrl = contributorsUrl ?: "",
        deploymentsUrl = deploymentsUrl ?: "",
        description = description ?: "",
        downloadsUrl = downloadsUrl ?: "",
        eventsUrl = eventsUrl ?: "",
        fork = fork == true,
        forksUrl = forksUrl ?: "",
        fullName = fullName ?: "",
        gitCommitsUrl = gitCommitsUrl ?: "",
        gitRefsUrl = gitRefsUrl ?: "",
        gitTagsUrl = gitTagsUrl ?: "",
        hooksUrl = hooksUrl ?: "",
        htmlUrl = htmlUrl ?: "",
        id = id ?: -1,
        issueCommentUrl = issueCommentUrl ?: "",
        issueEventsUrl = issueEventsUrl ?: "",
        issuesUrl = issuesUrl ?: "",
        keysUrl = keysUrl ?: "",
        labelsUrl = labelsUrl ?: "",
        languagesUrl = languagesUrl ?: "",
        mergesUrl = mergesUrl ?: "",
        milestonesUrl = milestonesUrl ?: "",
        name = name ?: "",
        nodeId = nodeId ?: "",
        notificationsUrl = notificationsUrl ?: "",
        owner = Owner(
            avatarUrl = owner?.avatarUrl ?: "",
            eventsUrl = owner?.eventsUrl ?: "",
            followersUrl = owner?.followersUrl ?: "",
            followingUrl = owner?.followingUrl ?: "",
            gistsUrl = owner?.gistsUrl ?: "",
            gravatarId = owner?.gravatarId ?: "",
            htmlUrl = owner?.htmlUrl ?: "",
            id = owner?.id ?: -1,
            login = owner?.login ?: "",
            nodeId = owner?.nodeId ?: "",
            organizationsUrl = owner?.organizationsUrl ?: "",
            receivedEventsUrl = owner?.receivedEventsUrl ?: "",
            reposUrl = owner?.reposUrl ?: "",
            siteAdmin = owner?.siteAdmin == true,
            starredUrl = owner?.starredUrl ?: "",
            subscriptionsUrl = owner?.subscriptionsUrl ?: "",
            type = owner?.type ?: "",
            url = owner?.url ?: "",
            userViewType = owner?.userViewType ?: "",
        ),
        `private` = `private` == true,
        pullsUrl = pullsUrl ?: "",
        releasesUrl = releasesUrl ?: "",
        stargazersUrl = stargazersUrl ?: "",
        statusesUrl = statusesUrl ?: "",
        subscribersUrl = subscribersUrl ?: "",
        subscriptionUrl = subscriptionUrl ?: "",
        tagsUrl = tagsUrl ?: "",
        teamsUrl = teamsUrl ?: "",
        treesUrl = treesUrl ?: "",
        url = url ?: "",
    )
}
