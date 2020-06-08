import gitbucket.core.view.Markdown
import gitbucket.core.plugin.{RenderRequest, Renderer}
import play.twirl.api.Html

class RMarkdownRenderer extends Renderer {

  def render(request: RenderRequest): Html = {
    import request._
    Html(
      Markdown.toHtml(
        markdown = fileContent,
        repository = repository,
        branch = branch,
        enableWikiLink = enableWikiLink,
        enableRefsLink = enableRefsLink,
        enableAnchor = enableAnchor,
        enableLineBreaks = false,
        enableTaskList = true,
        hasWritePermission = false
      )(context)
    )
  }

  def shutdown(): Unit = {
  }

}
