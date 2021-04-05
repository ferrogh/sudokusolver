import subprocess


def worker():

    for i in range(5):
        # job = q.get()  # q is a global Queue of jobs
        print("Starting process %d" % i)
        proc = subprocess.Popen(["echo", "haha"])
        proc.wait()
        print("Finished process %d" % i)


if __name__ == "__main__":
    worker()
    worker()