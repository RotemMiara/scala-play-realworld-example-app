package core.articles.config

import core.articles.ArticleComponents
import core.articles.models.{Article, NewArticle}
import core.articles.repositories.ArticleRepo
import commons.repositories.ActionRunner
import testhelpers.TestUtils
import core.users.models.UserId

import scala.concurrent.duration.DurationInt

trait ArticleTestComponents {
  _: ArticleComponents =>

  lazy val articlePopulator: ArticlePopulator = new ArticlePopulator(articleRepo, actionRunner)

}

class ArticlePopulator(articleRepo: ArticleRepo,
                       implicit private val actionRunner: ActionRunner) {

  def save(article: NewArticle): Article = {
    val action = articleRepo.create(article.toArticle)
    TestUtils.runAndAwaitResult(action)(actionRunner, new DurationInt(1).minute)
  }
}

object Articles {
  val hotToTrainYourDragon: NewArticle = NewArticle(
    "how-to-train-your-dragon",
    "how-to-train-your-dragon",
    "Ever wonder how?",
    "It takes a Jacobian",
    UserId(-1)
  )
}