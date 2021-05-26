import wartremover.{Wart, Warts}

object Wartremover {

  val defaultWarts: Seq[Wart] = Warts.allBut(
    Wart.DefaultArguments,
    Wart.ToString,
    Wart.ImplicitParameter,
    Wart.PublicInference,
    Wart.Null,
    Wart.JavaSerializable
  )

}
