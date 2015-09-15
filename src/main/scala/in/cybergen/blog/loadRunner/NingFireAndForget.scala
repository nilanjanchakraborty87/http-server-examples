package in.cybergen.blog.loadRunner
import java.util.concurrent.atomic.AtomicLong

import com.ning.http.client.{Response, AsyncCompletionHandler, AsyncHttpClient}

/**
 * Class in.cybergen.blog.loadRunner.Main
 * Created by vishnu667 on 10/9/15.
 */
object NingFireAndForget extends App {

  val successCounter:AtomicLong = new AtomicLong()
  val delayCounter:AtomicLong = new AtomicLong()
  val errorCounter:AtomicLong = new AtomicLong()

  val asyncHttpClient = new AsyncHttpClient()
  def fireAndForget(url: String) = asyncHttpClient.prepareGet(url).execute(
    new AsyncCompletionHandler[Unit]() {
      @throws(classOf[Exception])
      override def onCompleted(response: Response): Unit = {
        successCounter.incrementAndGet()
        delayCounter.addAndGet(System.currentTimeMillis() - response.getResponseBody.toLong)
      }

      override def onThrowable(t: Throwable): Unit = {
        errorCounter.incrementAndGet()
      }
    }).get()


  val startTime = System.currentTimeMillis()
  (1 to 1000000).par.foreach(i => fireAndForget("http://localhost:8080/?time="+System.currentTimeMillis()) )
  val endTime = System.currentTimeMillis()

  println("Time Taken "+(endTime-startTime))
  println("Counter success "+successCounter.get())
  println("Counter delay "+delayCounter.get())
  println("Counter error "+errorCounter.get())
  println("Time Taken for Complete Shutdown "+(System.currentTimeMillis()-startTime))
}
