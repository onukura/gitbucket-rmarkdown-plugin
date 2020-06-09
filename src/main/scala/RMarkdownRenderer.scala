import gitbucket.core.controller.Context
import gitbucket.core.view.Markdown
import gitbucket.core.plugin.{RenderRequest, Renderer}
import gitbucket.core.service.RepositoryService.RepositoryInfo
import play.twirl.api.Html

class RMarkdownRenderer extends Renderer {

  def render(request: RenderRequest): Html = {
    import request._
    Html(toHtml(filePath, fileContent, branch, repository, enableWikiLink, enableRefsLink, enableAnchor)(context))
  }

  def toHtml(
              filePath: List[String],
              fileContent: String,
              branch: String,
              repository: RepositoryInfo,
              enableWikiLink: Boolean,
              enableRefsLink: Boolean,
              enableAnchor: Boolean)(implicit context: Context): String = {

    val markdown = fileContent
      .replaceAll("^---\\s*\ntitle:(.*)","# $1\n\n```")
      .replaceAll("\n---\\s*\n", "\n```\n")

    Markdown.toHtml(
      markdown = markdown,
      repository = repository,
      branch = branch,
      enableWikiLink = enableWikiLink,
      enableRefsLink = enableRefsLink,
      enableAnchor = enableAnchor,
      enableLineBreaks = false,
      enableTaskList = true,
      hasWritePermission = false
    )(context)
  }

  def shutdown(): Unit = {
  }

}
