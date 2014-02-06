(ns manager.entries)

(def entries
  '{"BibTekKonf BibGraph" [:slides
                           :date "2013-10-26"
                           :desc "Slides fra BibTekKonf præsentation om visualisering ud fra ADHL-data"]
    :summerhacks [:slides]
    :html5-cnug [:slides]
    :js-engines [:slides]
    :berlincompiler-yolan [:slides]
    :osd-html5 [:slides]
    :osd-javascript [:slides]

    :jsonml2html [:lib :npmjs]
    :hack4dk-image-recog [:lib
                          :date "2013-09-28"]
    :uccorg-backend [:lib]
    :jsxml [:lib]

    :grundat [:notes :github "rasmusjensen/grundat"]
    :productivity-hacks [:notes]
    :pricing-scale [:notes]
    :fototips [:notes]
    :skrivetips [:notes]
    :presentation-evaluation [:notes]

    :structcomp [:thesis :bsc]
    :moblang [:thesis :msc]
    :iva-paper [:article]
    :tempo-paper [:article]

    :eurocards [:physical]
    :combigame-cards [:physical]

    :lightscript2 [:lang]
    :yolan2 [:lang]
    :lightscript [:lang :thesis]
    :yolan [:lang :thesis]
    :stack-language [:lang]
    :f1k [:lang :js1k]

    :onetwo360-client [:widget]
    :bibgraph [:widget]
    :speeding-viz [:widget]
    :dragimation [:widget]

    :combigame [:app]
    :blobshot [:app]
    :tsartnoc [:app]
    :dkcities [:app]
    :hack4dk-art-quiz [:app
                       :date "2013-09-28"]
    :notescore [:app]
    :vejviser-korrektur [:app]
    :vejviser-scale-workflow [:app]

    :massdrive [:protoapp]
    :skolevej [:protoapp]
    :find-a-day [:protoapp]
    :cuteengine [:protoapp]
    :bibdata [:protoapp]
    :jurapp [:protoapp]

    :f1k-rain [:demo]
    :f1k-brownian [:demo]
    :f1k-sierp [:demo]
    :fractal-4d [:demo]
    :yolan-editor [:demo]
    :treelayout [:demo]
    :hier-brows [:demo]
    :force-based-graph [:demo]
    :diamond-square-plasme [:demo]
    :hack4dk-wikipedia-viz [:demo
                            :date "2013-09-28"]

    :caracafu [:music "061227-ca-ra-ca-fu" :digitisation :not-by-me
               :desc "Digitisation/typesetting of an out-of-copyright tango score"]
    :music-070113 [:music "070113" :classical]
    :music-131023 [:music "131023" :classical]
    :tango-tema-del-ultimo-2006 [:music "061231-tango-tema" :tango]
    :untitled-nuevo-tema [:music "061231-tango-nuevo-tema" :tango]
    :a-tango-unfinished [:music "070113-tango" :tango]
    :mmm [:music "131023" :sad]
    :elala [:music "070102-elala" :classical :chord-progression]
    :rosa [:music "070101-rosa.midi" :tango]
    :drommespejl [:music "061231-song" :sad]
    :github-music [:meta :github "music" :build "lilypond *.ly"]
})
(comment {
    :rhino-e4x [:oss]
    :firefox-e4x [:oss]
    :js-beautify [:oss]
    :meteor-litcoffee [:oss]
})

; later
; - poems
; - image collections
; - building-home-stuff