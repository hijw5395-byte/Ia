from pathlib import Path
import sys, html
root = Path(sys.argv[1])
files = [p for p in root.rglob('*') if p.suffix.lower() in ('.htm', '.html')]
if not files:
    raise SystemExit('No se encontraron páginas HTML en el paquete Reina-Valera 1909.')
preferred = ['index.htm', 'index.html', 'spaRV1909.htm', 'spaRV1909.html', 'GEN.htm', 'GEN.html']
start = next((p for name in preferred for p in files if p.name.lower() == name.lower()), None)
if start is None:
    start = min(files, key=lambda p: (len(p.relative_to(root).parts), str(p).lower()))
rel = start.relative_to(root).as_posix()
page = '<!doctype html><html lang="es"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Biblia Reina-Valera 1909</title><style>html,body{margin:0;height:100%;background:#f7f3ea}iframe{border:0;width:100%;height:100%}</style></head><body><iframe title="Biblia Reina-Valera 1909" src="' + html.escape(rel, quote=True) + '"></iframe></body></html>'
(root / 'reader.html').write_text(page, encoding='utf-8')
print(f'Índice bíblico: {rel}; páginas HTML: {len(files)}')
