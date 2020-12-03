if [ ! -e "tmp" ]
then
    mkdir tmp
fi
if [ ! -e "output" ]
then
    mkdir output
fi
cp -R ./assets/. ./tmp/
find ./specifications/software-design/ -type f \( ! -iname "*md" -and ! -iname "*puml" \) -exec cp {} ./tmp/ \;
find ./specifications/software-design/ -type f \( -iname "*md" \) -exec sed "s/[0-9].\([0-9].\?\)* //g" {} >> ./tmp/reports-fragments/software-design-intro.md \;
cd ./tmp
cat ./reports-fragments/software-design-outro.md >> ./reports-fragments/software-design-intro.md
pandoc ./reports-fragments/software-design-intro.md \
    -f markdown+yaml_metadata_block \
    --template eisvogel \
    --table-of-contents \
    --toc-depth 6 \
    --number-sections \
    --top-level-division=chapter \
    --highlight-style breezedark \
    -o ../output/software-design-report.pdf
cd ..
rm -rf ./tmp