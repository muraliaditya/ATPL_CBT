FROM python:3.11-slim
RUN useradd -m -u 1000 runner || true
USER runner
WORKDIR /home/runner
RUN mkdir -p /home/runner/sandbox && chmod 700 /home/runner/sandbox
VOLUME ["/home/runner/sandbox"]
ENTRYPOINT ["/bin/sh","-c"]