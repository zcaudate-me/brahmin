(ns brahmin.api-guide)

[[:chapter {:title "Introduction"}]]

"[brahmin](https://github.com/purnam/brahmin) is a port of the fabulous  [fluokitten](https://github.com/uncomplicate/fluokitten) to clojurescript. It plays nicely with [purnam](https://github.com/purnam/purnam) and was implemented in order to better understand category theory. 
"

[[:chapter {:title "Usage"}]]

"To use just the test functionality, add to `project.clj` dependencies:

  `[im.chit/brahmin` \"`{{PROJECT.version}}`\"`]` "

"Better documentation will come later. You'll have to make do with the output of unit-tests:"

[[:chapter {:title "API"}]]

[[:section {:title "Functor - fmap" :tag "functior"}]]

[[:file {:src "test/brahmin/test_functor.cljs"}]]

[[:section {:title "Applicative - pure and fapply" :tag "applicative"}]]

[[:file {:src "test/brahmin/test_applicative.cljs"}]]

[[:section {:title "Magma - op" :tag "magma"}]]

[[:file {:src "test/brahmin/test_magma.cljs"}]]

[[:section {:title "Monoid - id" :tag "monoid"}]]

[[:file {:src "test/brahmin/test_monoid.cljs"}]]

[[:section {:title "Monad - bind and join" :tag "monad"}]]

[[:file {:src "test/brahmin/test_monad.cljs"}]]

[[:section {:title "Foldable - fold" :tag "foldable"}]]

[[:file {:src "test/brahmin/test_foldable.cljs"}]]
